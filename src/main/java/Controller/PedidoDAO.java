package Controller;

import Model.Fecha;
import Model.Pedido;
import Model.Producto;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

import static Controller.ProductoDAO.*;
import static View.View.*;


public class PedidoDAO {
    protected static final Connection con = Conexion.getCon();


    /**
     * Muestra toda la informacion de un pedido sin importar su estado
     *
     * @param identificador Identificador del pedido
     * @return Devuelve un Pedido:
     * <ul>
     *     <li>id</li>
     *     <li>identificador</li>
     *     <li>fecha</li>
     *     <li>cliente</li>
     *     <li>estado</li>
     *     <li>listado de productos</li>
     * </ul>
     */
    static Pedido infoPedido(Integer identificador) {
        HashMap<Producto, Integer> productos = new HashMap<>();
        Pedido pedido = new Pedido();
        String sql_query = "SELECT * FROM pedido WHERE identificador=?";

        try (PreparedStatement ps = con.prepareStatement(sql_query);) {
            ps.setInt(1, identificador);
            ResultSet rst = ps.executeQuery();
            while (rst.next()) {


                pedido.setId(rst.getInt("id"));
                pedido.setIdentificacion(rst.getInt("identificador"));
                pedido.setFecha(rst.getDate("fecha"));
                pedido.setCliente(rst.getString("cliente"));
                pedido.setEstado(rst.getString("estado"));
                productos.put(infoProducto(rst.getInt("producto")), 0);
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
        String sql_query = "INSERT INTO pedido (fecha,cliente,estado,producto,identificador) VALUES (?,?,?,?,?);";
        LinkedList<Integer> id_productos = new LinkedList<>();
        pedido.getProductos().forEach((k, v) -> {
            if (v > 0) {
                id_productos.add(k.getId());
            }
        });

        for (Integer id_producto : id_productos) {

            try (PreparedStatement pst = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);) {
                pst.setDate(1, pedido.getFecha());
                pst.setString(2, pedido.getCliente());
                pst.setString(3, pedido.getEstado());
                pst.setInt(4, id_producto);
                pst.setInt(5, pedido.getIdentificacion());

                pst.executeUpdate();
                ResultSet generatedKeys = pst.getGeneratedKeys();
                if (generatedKeys.next()) {
                    Integer id = generatedKeys.getInt(1);
                    pedido.setId(id);
                }
                finalizado = true;
            } catch (Exception e) {
                finalizado = false;
                throw new RuntimeException(e);
            }
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
        String nombre = leerString();

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

            comanda.forEach((k, v) -> {
                if (v > 0) {
                    System.out.println(k.getNombre() + " -> Cantidad: " + v);
                }
            });
            clean(1);

            System.out.println("Pulse enter para realizar otra comanda. Escriba 'exit' para pasar continuar");
            System.out.println("En caso de que quiera editar, usar un '-' delante del valor para quitar. Introducir el valor sin signo para sumar a lo ya pedido");
            salir = leerString();
        }

        cleanDot(4);
        System.out.println("Verifique que los datos a insertar son correctos: ");
        clean(1);
        System.out.println("Nombre de pedido: " + nombre);
        comanda.forEach((k, v) -> {
            if (v > 0) {
                System.out.println(k.getNombre() + " (" + k.getPrecio() + "€)" + " -> Cantidad: " + v);
            }
        });
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
            pedido.setEstado("pendiente");
            pedido.setFecha(sqlDate);
            clean(2);
            System.out.println("**********************************");
            pedido.recibo();
            System.out.println("**********************************");

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
    static void eliminarPedido(Integer identificador) {
        boolean finalizado = false;
        String sql_query = "DELETE FROM pedido WHERE identificador=?";
        try (PreparedStatement pst = con.prepareStatement(sql_query);) {
            pst.setInt(1, identificador);
            pst.execute();
            finalizado = true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Crea la interfaz grafica que permite al usuario eliminar los pedidos que desee. Para ello, muestra los pedidos existentes hasta la fecha. Da la opcion de
     * eleccion y, posteriormente, se pide una verificacion. Sea cual fuere la opcion elegida, da oportunidad de seguir eliminando pedidos o de volver al
     * menu previo
     */
    public static void gestorEliminacionPedido() {
        boolean salir = false;
        while (!salir) {
            ArrayList<Pedido> lista_pedidos = listarAllPedidos();

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
            System.out.println("¿Desea eliminar el siguiente pedido de manera permanente?");
            System.out.println(infoPedido(eleccion).infoView());
            System.out.println("0. No");
            System.out.println("1. Si");
            Integer aceptar = leerInt();

            if (aceptar == 1) {
                eliminarPedido(eleccion);
                lista_pedidos = listarAllPedidos();
                if (!lista_pedidos.isEmpty()) {
                    lista_pedidos.forEach((v) -> {
                        System.out.println(v.infoView());
                    });
                } else {
                    System.out.println("No hay Pedidos");
                }

                System.out.println("¿Desea continuar eliminando pedidos?");
                System.out.println("0. No");
                System.out.println("1. Si");
                aceptar = leerInt();
                if (aceptar == 0) {
                    salir = true;
                }
            }
        }
    }

    /**
     * Permite marcar un pedido como recogido
     *
     * @return <ul>
     * <li><i>True</i>: Si se ha sido recogido y marcado como tal</li>
     * <li><i>False</i>: Si se ha producido un error a la hora de recoger el pedido</li>
     * </ul>
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
            Integer eleccion = leerInt();
            if (eleccion == 1) {
                clean(3);
                recogerPedido(identificador_pedido);
                System.out.println("Situacion actual de los pedidos");
                listarAllPedidos().forEach(k -> {
                    System.out.println(k.infoView());
                });
                cleanDot(3);
            }
            System.out.println("¿Desea continuar marcando pedidos recogidos?");
            System.out.println("0. No");
            System.out.println("1.Si");
            eleccion = leerInt();
            if (eleccion == 0) {
                salir = true;
            }
        }
    }

    static void recogerPedido(Integer identificador_pedido) {
        String sql_query = "UPDATE pedido SET estado='recogido' WHERE identificador=?;";
        try (PreparedStatement pst = con.prepareStatement(sql_query)) {
            pst.setInt(1, identificador_pedido);
            pst.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Muestra todas las comandas hechas desde el principio:
     *
     * @return Hashmap con:
     * <ul>
     *     <li><b>Key: </b> Id del Pedido</li>
     *     <li><b>Value: Pedido</b><ul>
     *     <li>Fecha</li>
     *     <li>Cliente</li>
     *     <li>Estado</li>
     *     <li>Productos comprados</li>
     *     <li>Precio total del pedido</li>
     * </ul></li>
     * </ul>
     */
    static ArrayList<Pedido> listarAllPedidos() {
        ArrayList<Pedido> listado_pedidos = new ArrayList<>();
        ArrayList<Integer> info_identificador = new ArrayList<>();
        String sql_query = "SELECT DISTINCT identificador FROM pedido";
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



    static void gestorFechas(Integer selector) {

        Fecha fecha_inicio = ControllerFecha.leerFecha();

        switch (selector) {
            case 0:
                // Listar Pedidos de [f1,f2]
                break;
            case 1:
                //Pedidos pendientes de Fecha
                break;
            case 2:
                //Pedidos de un dia concreto
                break;

        }
    }

    /**
     * Muestra todas las comandas de una fecha en concreto
     *
     * @param fechaInicio Fecha desde la cual se desean obtener las comandas
     * @param fechaFin    Fecha final de la que de desea hacer la consulta
     * @return ArrayList de los pedidos comprendidos desde la fecha de inicio hasta la fecha de fin; ambas inclusive
     */
    static ArrayList<Pedido> listarPedidosFecha(Date fechaInicio, Date fechaFin) {
        ArrayList<Pedido> lista_pedidos = new ArrayList<>();
        String sql_query = "SELECT * FROM pedido where fecha>=? AND fecha<=?;";
        try (PreparedStatement pst = con.prepareStatement(sql_query)) {
            java.sql.Date sql_date_inicio = (java.sql.Date) fechaInicio;
            java.sql.Date sql_date_fin = (java.sql.Date) fechaFin;
            pst.setDate(1, sql_date_inicio);
            pst.setDate(2, sql_date_fin);
            ResultSet rst = pst.executeQuery();
            while (rst.next()) {
                Pedido pedido = new Pedido();
                Integer id = (rst.getInt("id"));
                pedido = infoPedido(id);
                lista_pedidos.add(pedido);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return lista_pedidos;

    }

    /**
     * Lista los pedidos asociados a un alumno siempre que estos hayan sido entregados
     *
     * @param nombre_alumno Nombre del alumno del que se desean obtener los pedidos
     * @return ArrayList de los pedidos asociados a un alumno
     */
//    static HashMap<Integer, Date> pedidosAlumno(String nombre_alumno) {
//
//    }

    /**
     * Accede a la id de todos los pedidos que se encuentran en estado: Pendiente
     */

    public static void pedidosPendientes() {
        ArrayList<Pedido> all_pedidos = listarAllPedidos();
        ArrayList<Pedido> pedidos_pendientes = new ArrayList<>();
        all_pedidos.forEach(k -> {
            if (k.getEstado().equals("pendiente")) {
                pedidos_pendientes.add(k);
            }
        });
        System.out.println("Lista de los pedidos actuales que se encuentran pendientes");
        pedidos_pendientes.forEach(k -> {
            System.out.println(k.infoView());
        });
    }

    /**
     * Accede a la id de todos los pedidos que se encuentran en estado: Pendiente de una fecha en concreto
     *
     * @param fecha Fecha de la que se desea obtener el listado
     * @return Listado de la id de todos aquellos pedidos que se encuentran pendientes.
     */
    static ArrayList<Integer> pedidosPendientesFecha(Date fecha) {

        return null;
    }

    static ArrayList<Integer> obtenerAllIdentificacion() throws Exception {
        ArrayList<Integer> todas_identificaciones = new ArrayList<>();
        String sql_query = "SELECT pedido.identificador FROM comanda_desayunos.pedido;";
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
