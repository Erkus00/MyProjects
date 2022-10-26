package controller;

import model.Fecha;
import model.Pedido;
import model.Producto;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

import static controller.ProductoDAO.*;
import static view.View.*;

/**
 * Clase de Control de los Pedidos
 */
public class PedidoDAO {
    // Objeto de conexion con la Base de Datos
    protected static final Connection con = Conexion.getCon();

    /**
     * Muestra toda la informacion de un pedido sin importar su estado
     *
     * @param identificador Identificador del pedido. Un mismo pedido comparte identificador
     * @return Devuelve el Pedido guardado en la Base de Datos con el Identificador Indicado
     */
    static Pedido infoPedido(Integer identificador) {
        HashMap<Producto, Integer> productos = new HashMap<>();
        Pedido pedido = new Pedido();
        String sql_query = "SELECT * FROM pedido WHERE identificador=?";

        try (PreparedStatement ps = con.prepareStatement(sql_query);) {
            ps.setInt(1, identificador);
            ResultSet rst = ps.executeQuery();
            while (rst.next()) {


                pedido.setIdentificacion(rst.getInt("identificador"));
                pedido.setFecha(rst.getDate("fecha"));
                pedido.setCliente(rst.getString("cliente"));
                pedido.setEstado(rst.getString("estado"));
                productos.put(infoProducto(rst.getInt("producto")), rst.getInt("cantidad"));
                pedido.setProductos(productos);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return pedido;
    }

    /**
     * Permite crear un nuevo Pedido, siempre que el producto exista
     *
     * @return <ul>
     * <li><i>True</i>: Si se ha podido Crear</li>
     * <li><i>False</i>: Si ha habido un error a la hora de crear el pedido</li>
     * </ul>
     */
    static boolean insertarPedido(Pedido pedido) {

        boolean finalizado = false;
        String sql_query = "INSERT INTO pedido (fecha,cliente,estado,producto,identificador,cantidad) VALUES (?,?,?,?,?,?);";
        LinkedList<Integer> id_productos = new LinkedList<>();
        LinkedList<Integer> cantidades = new LinkedList<>();
        pedido.getProductos().forEach((k, v) -> {
            if (v > 0) {
                id_productos.add(k.getId());
                cantidades.add(v);
            }
        });
        int i = 0;
        for (Integer id_producto : id_productos) {

            try (PreparedStatement pst = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);) {
                pst.setDate(1, pedido.getFecha());
                pst.setString(2, pedido.getCliente());
                pst.setString(3, pedido.getEstado());
                pst.setInt(4, id_producto);
                pst.setInt(5, pedido.getIdentificacion());
                pst.setInt(6, cantidades.get(i));
                int j = pst.executeUpdate();
                if (j > 0) {
                    finalizado = true;
                } else {
                    System.out.println("Algo ha fallado al insertar el Producto");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);

            }

            i++;
        }
        return finalizado;
    }

    /**
     * Permite la gestion para la insercion de un pedido. El programa pide por consola un nombre para el pedido, muestra la carta disponible y permite al
     * usuario modificar la comanda antes de enviarla. Antes de guardarla en la base de datos (hacer el 'INSERT') se pide una ultima verificacion.
     * Imprime por pantalla el recibo de la compra y permite volver a hacer otro pedido o volver a la pantalla anterior
     */

    public static void insercionPedido() throws Exception {

        // Lista que guardará como Key: el producto y como segundo valor el numero de elementos que se desea
        LinkedHashMap<Producto, Integer> comanda = new LinkedHashMap<>();
        Pedido pedido = new Pedido();

        System.out.println("Indique un nombre de pedido");
        String nombre = leerString().toUpperCase();

        clean(1);
        Integer id_producto;
        Integer cant = 0;
        String salir = "";

        while (!salir.equals("exit")) {
            System.out.println("--------------------------------");
            carta().forEach((v) -> {
                System.out.println(v.cartaView());
            });
            System.out.println("--------------------------------");


            clean(1);
            System.out.println("Indiqueme el numero de producto");
            id_producto = leerInt();
            System.out.println("Cantidad: ");
            cant = leerInt();

            if (!comanda.isEmpty()) {
                Integer checked_producto = id_producto;
                Integer finalCant = cant;
                AtomicBoolean nuevo = new AtomicBoolean(true);

                comanda.forEach((k, v) -> {
                    Integer temp = k.getId();
                    if (Objects.equals(temp, checked_producto)) {
                        nuevo.set(false);
                        Integer actualizar_cant = v + finalCant;
                        comanda.replace(k, actualizar_cant);
                    }
                });
                if (nuevo.get()) {
                    Producto prod = infoProducto(id_producto);
                    comanda.put(prod, cant);
                }
            } else {
                Producto prod = infoProducto(id_producto);
                comanda.put(prod, cant);
            }

            clean(1);
            System.out.println("/////////////////////////////////////////////////////");
            clean(1);
            System.out.println("Productos guardados:");
            cleanDot(1);
            comanda.forEach((k, v) -> {
                if (v > 0) {
                    System.out.println(k.getNombre() + " -> Cantidad: " + v);
                }
            });
            clean(1);

            System.out.println("Pulse enter para realizar otra comanda. Escriba 'exit' para pasar al proceso de confirmacion");
            System.out.println("<< Nota: En caso de que quiera editar, usar un '-' delante del valor para quitar. Introducir el valor sin signo, habiendo seleccionado previamente el producto, para sumar a lo ya pedido >>");
            salir = leerString();
        }

        cleanDot(4);
        System.out.println("Verifique que los datos a insertar son correctos: ");
        clean(1);
        System.out.println("/////////////////////////////////////////////////////");
        cleanDot(1);
        System.out.println("Nombre de pedido: " + nombre);
        comanda.forEach((k, v) -> {
            if (v > 0) {
                System.out.println(k.getNombre() + " (" + k.getPrecio() + "€)" + " -> Cantidad: " + v);
            }
        });
        cleanDot(1);
        System.out.println("/////////////////////////////////////////////////////");
        clean(1);
        System.out.println("Pulse 1 para imprimir el recibo. Pulse 0 para salir al menu de ingreso");
        Integer lect = leerInt();
        if (lect == 1) {
            boolean insertado;
            Date now = new Date();
            java.sql.Date sqlDate = new java.sql.Date(now.getTime());
            ArrayList<Integer> identificaciones = obtenerAllIdentificacion();
            Integer max;

            if (!identificaciones.isEmpty()) {
                max = Collections.max(identificaciones);
            } else {
                max = 0;
            }

            pedido.setCliente(nombre);
            pedido.setIdentificacion(max + 1);
            pedido.setProductos(comanda);
            pedido.setEstado("PENDIENTE");
            pedido.setFecha(sqlDate);
            clean(2);
            pedido.recibo();
            insertado = insertarPedido(pedido);
            if (insertado) {
                System.out.println("Pedido realizado correctamente");
            } else {
                System.out.println("Problema al realizar el pedido. Intentelo de Nuevo por favor y disculpe las molestias");
            }
            clean(1);
        }
    }

    /**
     * Permite eliminar un pedido siempre que este ya se haya realizado
     */
    static boolean eliminarPedido(Integer identificador) {
        boolean finalizado = false;
        String sql_query = "DELETE FROM pedido WHERE identificador=?";
        try (PreparedStatement pst = con.prepareStatement(sql_query);) {
            pst.setInt(1, identificador);
            int i = pst.executeUpdate();
            if (i > 0) {
                finalizado = true;
            } else {
                System.out.println("Algo ha fallado al insertar el Producto");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return finalizado;
    }

    /**
     * Crea la interfaz grafica que permite al usuario eliminar los pedidos que desee. Para ello, muestra los pedidos existentes hasta la fecha. Da la opcion de
     * eleccion y, posteriormente, se pide una verificacion. Sea cual fuere la opcion elegida, da oportunidad de seguir eliminando pedidos o de volver al
     * menu previo
     */
    public static void gestorEliminacionPedido() {
        boolean salir = false;
        while (!salir) {
            ArrayList<Pedido> lista_pedidos = listarAllPedidos(false);
            clean(2);
            System.out.println("--- Que pedido desea eliminar");
            if (!lista_pedidos.isEmpty()) {
                lista_pedidos.forEach((v) -> {
                    System.out.println(v.infoView());
                });
            } else {
                System.out.println("No hay Pedidos");
            }
            System.out.println("----------------------------------------------------------------------");
            System.out.println("Indiqueme el numero de identificacion que desea eliminar");
            Integer eleccion = leerInt();
            System.out.println("¿Desea eliminar el siguiente pedido de manera permanente? [y/n]");
            System.out.println(infoPedido(eleccion).infoView());
            System.out.println("y -> Si");
            System.out.println("n -> No");
            String aceptar = leerString().toLowerCase();

            if (aceptar.equals("y")) {
                boolean eliminado = eliminarPedido(eleccion);
                lista_pedidos = listarAllPedidos(false);
                if (!lista_pedidos.isEmpty() && eliminado) {
                    lista_pedidos.forEach((v) -> {
                        System.out.println(v.infoView());
                    });
                } else {
                    if (!eliminado) {
                        System.out.println("Error de eliminacion");
                    } else {
                        clean(2);
                        System.out.println("No hay Pedidos");
                        clean(3);
                    }
                }

                System.out.println("¿Desea continuar eliminando pedidos?");
                System.out.println("y -> Si");
                System.out.println("n -> No");
                aceptar = leerString().toLowerCase();
                if (aceptar.equals("y")) {
                    salir = true;
                }
            }
        }
    }

    /**
     * Permite marcar un pedido como recogido y guia al usuario en la eleccion
     * Una vez finalizada la funcion, interactua con el usuario para volver a ejecutarse en el caso que este lo desee
     */
    public static void marcarPedidoRecogido() {
        boolean salir = false;
        while (!salir) {
            pedidosPendientes();
            System.out.println("Indique el Id del pedido que desea marcar como recogido");
            Integer identificador_pedido = leerInt();
            System.out.println("¿Desea marcar como recogido este pedido?");
            System.out.println(infoPedido(identificador_pedido));
            System.out.println("0. No");
            System.out.println("1.Si");
            int eleccion = leerInt();
            if (eleccion == 1) {
                clean(3);
                recogerPedido(identificador_pedido);
                System.out.println("Situacion actual de los pedidos");
                listarAllPedidos(false).forEach(k -> {
                    System.out.println(k.infoView());
                });
                cleanDot(3);
            }
            System.out.println("¿Desea continuar marcando pedidos recogidos?");
            System.out.println("0. No");
            System.out.println("1. Si");
            eleccion = leerInt();
            if (eleccion == 0) {
                salir = true;
            }
        }
    }

    /**
     * Marca un pedido como recogido y actualiza la BD
     *
     * @param identificador_pedido Numero de identificacion del pedido que se desea recoger
     */
    static void recogerPedido(Integer identificador_pedido) {
        String sql_query = "UPDATE pedido SET estado='RECOGIDO' WHERE identificador=?;";
        try (PreparedStatement pst = con.prepareStatement(sql_query)) {
            pst.setInt(1, identificador_pedido);
            pst.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Muestra todas las comandas.
     * Si se desea, puede hacerse consulta de aquellos pedidos que sean exclusivamente pendientes,
     * en caso contrario, la consulta devolverá todos los pedidos existentes
     */
    static ArrayList<Pedido> listarAllPedidos(boolean pendiente) {
        ArrayList<Pedido> listado_pedidos = new ArrayList<>();
        ArrayList<Integer> info_identificador = new ArrayList<>();
        String sql_query = "";
        if (!pendiente) {
            sql_query = "SELECT DISTINCT identificador FROM pedido";
        } else {
            sql_query = "SELECT DISTINCT identificador FROM pedido WHERE estado='PENDIENTE'";
        }

        try (PreparedStatement pst = con.prepareStatement(sql_query)) {
            ResultSet rst = pst.executeQuery();
            while (rst.next()) {
                info_identificador.add(rst.getInt("identificador"));
            }
            info_identificador.forEach(k -> {
                Pedido pedido_temp = infoPedido(k);
                listado_pedidos.add(pedido_temp);
            });

        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }

        return listado_pedidos;
    }

    /**
     * Gestiona la entrada por teclado de una fecha.
     * Dependiendo de la opcion, se introducen 2 fechas (caso 0) o 1 sola (caso 1)
     *
     * @param selector Caso que se desea mostrar al usuario
     */
    public static void gestorFechas(Integer selector) {
        switch (selector) {
            case 0 -> {
                //Pedidos de un intervalo de fechas [f1,f2]
                System.out.println("Siga los pasos para indicar la fecha de inicio de consulta");
                Fecha fecha_inicio = ControllerFecha.leerFecha();
                System.out.println("Siga los pasos para indicar la fecha de fin de consulta");
                Fecha fecha_fin = ControllerFecha.leerFecha();
                listarPedidosFecha(fecha_inicio.getSql_date(), fecha_fin.getSql_date()).forEach(k -> {
                    System.out.println(k.infoView());
                });
            }
            case 1 -> {
                //Pedidos pendientes de Fecha
                System.out.println("Siga los pasos para indicar la fecha de consulta");
                Fecha fecha = ControllerFecha.leerFecha();
                pedidosPendientesFecha(fecha.getSql_date());
            }
        }
    }

    /**
     * Muestra todas las comandas de una fecha en concreto
     *
     * @param fechaInicio Fecha desde la cual se desean obtener las comandas
     * @param fechaFin    Fecha final de la que de desea hacer la consulta
     * @return ArrayList de los pedidos comprendidos desde la fecha de inicio hasta la fecha de fin; ambas inclusive
     */
    static ArrayList<Pedido> listarPedidosFecha(java.sql.Date fechaInicio, java.sql.Date fechaFin) {
        ArrayList<Pedido> lista_pedidos = new ArrayList<>();
        String sql_query = "";
        sql_query = "SELECT DISTINCT identificador FROM pedido WHERE fecha>=? AND fecha<=?;";
        try (PreparedStatement pst = con.prepareStatement(sql_query)) {
            pst.setDate(1, fechaInicio);
            pst.setDate(2, fechaFin);
            ResultSet rst = pst.executeQuery();
            while (rst.next()) {
                Pedido pedido = new Pedido();
                Integer id = (rst.getInt("identificador"));
                pedido = infoPedido(id);
                lista_pedidos.add(pedido);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return lista_pedidos;

    }

    /**
     * Muestra los diferentes clientes y permite
     */
    public static void gestorConsultaAlumno() {
        clean(3);
        System.out.println("Listado de los Alumnos: ");
        clean(1);
        listarAlumnos();
        System.out.println("Indique el nombre del alumno");
        String alumno = leerString().toUpperCase();
        pedidosAlumno(alumno).forEach(k -> {
            System.out.println(k.infoView());
        });
    }

    /**
     * Lista los pedidos asociados a un alumno siempre que estos hayan sido entregados
     *
     * @param nombre_alumno Nombre del alumno del que se desean obtener los pedidos
     * @return ArrayList de los pedidos asociados a un alumno
     */
    static ArrayList<Pedido> pedidosAlumno(String nombre_alumno) {
        ArrayList<Pedido> pedidos_alumno = new ArrayList<>();
        String sql_query = "SELECT DISTINCT identificador FROM pedido WHERE cliente=?";
        try (PreparedStatement pst = con.prepareStatement(sql_query)) {
            pst.setString(1, nombre_alumno);
            ResultSet rst = pst.executeQuery();
            while (rst.next()) {
                Integer id = rst.getInt("identificador");
                Pedido pedido = new Pedido();
                pedido = infoPedido(id);
                pedidos_alumno.add(pedido);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return pedidos_alumno;
    }

    /**
     * Lista y muestra los clientes que han efectuado uno o mas pedidos
     */
    private static void listarAlumnos() {
        ArrayList<String> alumnos = new ArrayList<>();
        String sql_query = "SELECT DISTINCT cliente FROM pedido";
        try (PreparedStatement pst = con.prepareStatement(sql_query)) {
            ResultSet rst = pst.executeQuery();
            while (rst.next()) {
                String alumno = rst.getString("cliente");
                alumnos.add(alumno);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        alumnos.forEach(k -> {
            System.out.println("-- " + k);
        });
    }


    /**
     * Consulta a la BD los pedidos que se encuentran pendientes
     */

    public static void pedidosPendientes() {
        ArrayList<Pedido> pedidos_pendientes = listarAllPedidos(true);

        System.out.println("Lista de los pedidos actuales que se encuentran pendientes");
        clean(2);
        pedidos_pendientes.forEach(k -> {
            System.out.println(k.infoView());
            clean(1);
        });
    }

    /**
     * Accede a la id de todos los pedidos que se encuentran en estado: Pendiente de una fecha en concreto.
     * Posteriormente los muestra
     *
     * @param fecha Fecha de la que se desea obtener el listado
     */
    static void pedidosPendientesFecha(java.sql.Date fecha) {
        ArrayList<Pedido> pedidos_pendientes = new ArrayList<>();
        String sql_query = "SELECT DISTINCT identificador FROM pedido WHERE estado='PENDIENTE' AND fecha=?";
        try (PreparedStatement pst = con.prepareStatement(sql_query)) {
            pst.setDate(1, fecha);
            ResultSet rst = pst.executeQuery();
            while (rst.next()) {
                Pedido pedido_temp = new Pedido();
                Integer id = rst.getInt("identificador");
                pedido_temp = infoPedido(id);
                pedidos_pendientes.add(pedido_temp);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("Listado de Pedidos Pendientes del " + fecha.toString());
        pedidos_pendientes.forEach(k -> {
            System.out.println(k.infoView());
        });
    }

    /**
     * Consulta los distintos Identificadores de todos los pedidos
     *
     * @return Listado de las distintas identificaciones
     */
    static ArrayList<Integer> obtenerAllIdentificacion() throws Exception {
        ArrayList<Integer> todas_identificaciones = new ArrayList<>();
        String sql_query = "SELECT DISTINCT identificador FROM comanda_desayunos.pedido;";
        try (PreparedStatement pst = con.prepareStatement(sql_query)) {
            ResultSet rest = pst.executeQuery();
            while (rest.next()) {
                todas_identificaciones.add(rest.getInt("identificador"));
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
        return todas_identificaciones;
    }
}
