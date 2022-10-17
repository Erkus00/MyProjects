package Main;

import Controller.Conexion;
import Model.Pedido;
import Model.Producto;

import javax.swing.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    protected static Boolean primera_vez = true;
    protected static final Connection con = Conexion.getCon();


    public static void main(String[] args) throws Exception {


        boolean salir = false;

        while (!salir) {
            Integer opcion = menuInicio();
            int elector;
            boolean salir_sub = false;
            cleanDot(5);

            switch (opcion) {
                case 0:
                    salir = true;
                    salir();
                    break;
                case 1:
                    while (!salir_sub) {
                        elector = menuConsulta();
                        cleanDot(3);
                        switch (elector) {
                            case 0 -> salir_sub = true;
                            case 1 -> System.out.println("Listar Comandas pendientes de un dia");
                            case 2 -> System.out.println("Mostrar todas las comandas pendientes");
                            case 3 -> System.out.println("Mostrar comandas Pendientes");
                            case 4 -> System.out.println("Mostrar comandas de un tramo");
                            case 5 -> System.out.println("Ver pedidos de un Alumno");
                            default -> {
                                clean(5);
                                System.out.println("Opcion no disponible");
                                clean(3);
                            }
                        }
                    }
                    break;
                case 2:

                    while (!salir_sub) {
                        elector = menuMod();
                        cleanDot(3);
                        switch (elector) {
                            case 0 -> salir_sub = true;
                            case 1 -> gestorEliminacionPedido();
                            case 2 -> System.out.println("Marcar pedido como recogido");
                            case 3 -> System.out.println("Modificar Pedido");
                            case 4 -> System.out.println("Modificar Producto");
                            default -> {
                                clean(5);
                                System.out.println("Opcion no disponible");
                                clean(3);
                            }
                        }
                    }
                    break;
                case 3:
                    while (!salir_sub) {
                        elector = menuIngreso();
                        clean(3);
                        switch (elector) {
                            case 0 -> salir_sub = true;
                            case 1 -> gestionPedido();
                            case 2 -> gestionProducto();
                            case 3 -> System.out.println("Rellenar Stock");
                            default -> {
                                clean(5);
                                System.out.println("Opcion no disponible");
                                clean(3);
                            }
                        }
                    }
                    break;
                default:
                    clean(3);
                    System.out.println("Opcion no disponible");
                    break;
            }
        }
    }

    // ------------------------------------------------------------------------------------------------------------------------------------------------------- //
    //Funciones de gestion

    static void gestionProducto() {

        boolean salir = false;
        String nombre = "";
        Integer tipo = 0;
        float precio = 0.0F;
        String correcto = "";
        boolean insertado = false;

        while (!salir) {

            Producto producto = new Producto();

            System.out.println("Indique el nombre del producto");
            nombre = leerString();

            System.out.println("Indique el Tipo");
            System.out.println("1. Bebida");
            System.out.println("2. Panaderia");
            System.out.println("3. Bolleria");
            System.out.println("4. Lacteo");
            System.out.println("5. Otro");
            tipo = leerInt();

            System.out.println("Indique el precio € -- (Formato Ejemplo: 1,5) --");
            precio = leerFloat();


            System.out.println("Especificaciones del producto");
            System.out.println("Nombre: " + nombre);
            System.out.println("Tipo: " + Producto.leerTipo(tipo));
            System.out.println("Precio: " + precio + "€");

            System.out.println("¿Son correctos estos parámetros? [y/n]");
            correcto = leerString().toLowerCase();

            if (correcto.equals("y")) {
                producto.setNombre(nombre.toLowerCase());
                producto.setTipo(tipo);
                producto.setPrecio(precio);
                producto.setDisponible(true);
                insertado = insertarProducto(producto);
            }
            clean(3);
            if (insertado) {
                System.out.println("Producto insertado correctamente");
                System.out.println(producto);
            } else {
                System.out.println("Producto no ha sido insertado");
            }
            clean(2);
            System.out.println("¿Desea añadir otro producto [y/n]?");
            cleanDot(1);
            System.out.println("y -> Sí");
            System.out.println("n -> No");
            String otro = leerString().toLowerCase();
            clean(1);
            if (otro.equals("n")) {
                salir = true;
            }
        }
    }

    static void gestionPedido() throws Exception {

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
        comanda.forEach((k, v) ->
        {
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
            ArrayList<Integer> identificaciones = new ArrayList<>();
            identificaciones = gestorIdentificacion();
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

    static ArrayList<Integer> gestorIdentificacion() throws Exception {
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

    static void gestorEliminacionPedido() {
        boolean salir = false;
        while (!salir) {
            ArrayList<Pedido> lista_pedidos = listarPedidos();

            System.out.println("--- Que pedido desea eliminar");
            lista_pedidos.forEach((v) -> {
                System.out.println(v.infoView());
            });
            System.out.println("----------------------------------------------------------------------");
            System.out.println("Indiqueme el numero de identificacion que desea eliminar");
            Integer eleccion = leerInt();
            System.out.println("¿Desea eliminar el siguiente pedido?");
            System.out.println(infoPedido(eleccion).infoView());
            System.out.println("0. No");
            System.out.println("1. Si");
            Integer aceptar = leerInt();

            if (aceptar == 1) {
                eliminarPedido(eleccion);
                lista_pedidos = listarPedidos();
                lista_pedidos.forEach((v) -> {
                    System.out.println(v.infoView());
                });

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

    // Funciones CRUD (Create, Read, Delete and Update

    // Funciones de insercion de Datos (Create)

    /**
     * Permite crear un nuevo Pedido siempre que el producto se encuentre disponible
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

        for (int i = 0; i < id_productos.size(); i++) {

            try (PreparedStatement pst = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);) {
                pst.setDate(1, pedido.getFecha());
                pst.setString(2, pedido.getCliente());
                pst.setString(3, pedido.getEstado());
                pst.setInt(4, id_productos.get(i));
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
     * Permite crear un nuevo Producto
     *
     * @return <ul>
     * <li><i>True</i>: Si se ha podido Crear</li>
     * <li><i>False</i>: Si ha habido algun error</li>
     * </ul>
     */
    static boolean insertarProducto(Producto producto) {
        boolean finalizado;

        String sql_query = "INSERT INTO carta (nombre,tipo,precio,disponibilidad) VALUES (?,?,?,?);";
        try (PreparedStatement pst = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);) {
            pst.setString(1, producto.getNombre());
            pst.setString(2, producto.getTipo());
            pst.setFloat(3, producto.getPrecio());
            pst.setBoolean(4, producto.isDisponible());
            pst.executeUpdate();
            ResultSet generatedKeys = pst.getGeneratedKeys();
            if (generatedKeys.next()) {
                Integer id = generatedKeys.getInt(1);
                producto.setId(id);
            }
            finalizado = true;
        } catch (Exception e) {
            finalizado = false;
            throw new RuntimeException(e);
        }
        return finalizado;
    }

    // Funciones de Eliminar Datos (Delete)

    /**
     * Permite eliminar un pedido siempre que este ya se haya realizado
     *
     * @return <ul>
     * <li><i>True</i>: Si se ha podido Eliminar</li>
     * <li><i>False</i>: Si ha habido un error a la hora de eliminar el pedido</li>
     * </ul>
     */
    static boolean eliminarPedido(Integer identificador) {
        boolean finalizado = false;
        String sql_query = "DELETE FROM pedido WHERE identificador=?";
        try (PreparedStatement pst = con.prepareStatement(sql_query);) {
            pst.setInt(1, identificador);
            finalizado = true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return finalizado;
    }

//    static boolean eliminarProducto(Integer id) {
//        boolean finalizado = false;
//        String sql_query = "DELETE FROM carta WHERE id=?";
//        try (PreparedStatement pst = con.prepareStatement(sql_query);) {
//            pst.setInt(1, id);
//            pst.executeUpdate();
//            finalizado = true;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        return finalizado;
//    }

    // Funciones de Actualizar Datos (Update)

    /**
     * Permite marcar un pedido como recogido
     *
     * @return <ul>
     * <li><i>True</i>: Si se ha sido recogido y marcado como tal</li>
     * <li><i>False</i>: Si se ha producido un error a la hora de recoger el pedido</li>
     * </ul>
     */
    static boolean marcarPedidoRecogido(Integer id_pedido) {
        boolean finalizado = false;
        return finalizado;
    }


    // Funciones de Leer/Coger de Datos (Read)

    /**
     * Genera una lista con todos los productos esten o no disponibles
     *
     * @return Listado de la informacion detallada de todos los productos
     */
    static ArrayList<Producto> listarProductos() {

        ArrayList<Producto> productos = new ArrayList<>();
        Producto prod;

        String sql_query = "SELECT * FROM comanda_desayunos.carta";

        try (PreparedStatement pst = con.prepareStatement(sql_query);) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                prod = infoProducto(rs.getInt("id"));
                productos.add(prod);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return productos;
    }

    /**
     * Lista aquellos productos que están disponibles y su precio
     *
     * @return Hashmap con:
     * <ul>
     *     <li><b>Key: </b>Nombre del Producto</li>
     *     <li><b>Value: </b>Producto</li>
     * </ul>
     */
    static ArrayList<Producto> carta() {
        ArrayList<Producto> carta = new ArrayList<>();
        Producto prod;

        String sql_query = "SELECT * FROM comanda_desayunos.carta WHERE disponibilidad=1;";
        try (PreparedStatement pst = con.prepareStatement(sql_query);) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                prod = infoProducto(rs.getInt("id"));
                carta.add(prod);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return carta;
    }

    /**
     * Muestra toda la informacion de un producto de la carta
     *
     * @param id_producto Identificador del producto
     * @return Muestra toda la informacion de un pruducto:
     * <ul>
     *     <li>Nombre</li>
     *     <li>Tipo</li>
     *     <li>Precio</li>
     *     <li>Disponibilidad</li>
     * </ul>
     */
    static Producto infoProducto(Integer id_producto) {
        Producto prod = new Producto();

        String sql_query = "SELECT * FROM comanda_desayunos.carta WHERE id=?;";
        try (PreparedStatement pst = con.prepareStatement(sql_query);) {
            pst.setInt(1, id_producto);
            ResultSet rest = pst.executeQuery();

            while (rest.next()) {
                prod.setId(id_producto);
                prod.setNombre(rest.getString("nombre"));
                prod.setTipo(rest.getString("tipo"));
                prod.setPrecio(rest.getFloat("precio"));
                prod.setDisponible(rest.getBoolean("disponibilidad"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return prod;
    }

    /**
     * Obtiene la disponibilidad de un producto del restaurante
     *
     * @param id_producto Identificador del producto del que se desea obtener la disponibilidad
     * @return <ul>
     * <li><i>True</i>: Si el producto se encuentra disponible</li>
     * <li><i>False</i>: Si el producto no está disponible</li>
     * </ul>
     */
//    static Boolean obtenerDisponibilidadProducto(Integer id_producto) {
//    }

    /**
     * Muestra toda la informacion de una comanda sin importar su estado
     *
     * @param identificador Identificador del pedido
     * @return Devuelve un Pedido:
     * <ul>
     *     <li>Fecha</li>
     *     <li>Cliente</li>
     *     <li>Estado</li>
     *     <li>Productos comprados</li>
     *     <li>Precio total del pedido</li>
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

    static ArrayList<Pedido> listarPedidos() {
        ArrayList<Pedido> listado_pedidos = new ArrayList<>();
        String sql_query = "SELECT MAX(identificador) AS maximo FROM pedido";
        try (PreparedStatement pst = con.prepareStatement(sql_query)) {
            ResultSet rst = pst.executeQuery();
            Integer max_id = null;
            while (rst.next()) {
                max_id = rst.getInt("maximo");

                for (Integer i = 1; i <= max_id; i++) {
                    Pedido pedido_temp = infoPedido(i);
                    listado_pedidos.add(pedido_temp);
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listado_pedidos;

    }

    /**
     * Muestra todas las comandas de una fecha en concreto
     *
     * @param fecha Fecha de la cual se desean obtener las comandas
     * @return Hashmap con:
     * <ul>
     *     <li><b>Key: </b> Id del Pedido</li>
     *     <li><b>Value: </b><ul>
     *     <li>Cliente</li>
     *     <li>Estado</li>
     *     <li>Productos comprados</li>
     *     <li>Precio total del pedido</li>
     * </ul></li>
     * </ul>
     */
//    static HashMap<Integer, String> listarPedidos(Date fecha) {
//
//    }


    /**
     * Lista los pedidos asociados a un alumno siempre que estos hayan sido entregados
     *
     * @param nombre_alumno Nombre del alumno del que se desean obtener los pedidos
     * @return Hashmap con:
     * <ul>
     *     <li><b>Key: </b> Id del Pedido</li>
     *     <li><b>Value: </b>Fecha en la que se realizó la comanda</li>
     * </ul>
     */
//    static HashMap<Integer, Date> pedidosAlumno(String nombre_alumno) {
//    }

    /**
     * Accede a la id de todos los pedidos que se encuentran en estado: Pendiente
     *
     * @return Listado de la id de todos aquellos pedidos que se encuentran pendientes
     */
//    static ArrayList<Integer> pedidosPendientes() {
//
//    }

    /**
     * Accede a la id de todos los pedidos que se encuentran en estado: Pendiente de una fecha en concreto
     *
     * @param fecha Fecha de la que se desea obtener el listado
     * @return Listado de la id de todos aquellos pedidos que se encuentran pendientes.
     */
//    static ArrayList<Integer> pedidosPendientes(Date fecha) {
//
//    }

    // Funciones Auxiliares

    /**
     * Muestra el menu inicial de acciones del programa por consola. En este podrá elegir si desea:
     * <ul>
     *     <li>0. Salir del programa</li>
     *     <li>1. Consultar Datos</li>
     *     <li>2. Modificar Datos</li>
     *     <li>3. Ingresar Datos</li>
     * </ul>
     *
     * @return La opcion seleccionada
     */
    static Integer menuInicio() {
        Scanner sc = new Scanner(System.in);
        cleanDot(4);
        if (primera_vez) {
            System.out.println("<<                                                        >>");
            System.out.println("BIENVENIDO AL PROGRAMA DE GESTION DE LOS DESAYUNOS DEL CESUR");
            primera_vez = false;
            System.out.println("<<                                                        >>");
        } else {
            System.out.println("<<                                                        >>");
        }
        System.out.println("Indique que desea hacer:");
        clean(1);
        System.out.println("0. Salir del programa");
        System.out.println("1. Consultar Datos");
        System.out.println("2. Modificar Datos");
        System.out.println("3. Ingresar Datos");
        clean(1);
        return sc.nextInt();

    }

    /**
     * Muestra el menu de las opciones de consulta a la base de datos. Estas serian:
     * <ul>
     *     <li>0. Salir al menu principal</li>
     *     <li>1. Listar comandas pendientes de un dia (Hoy de manera predeterminada)</li>
     *     <li>2. Mostrar todas las comandas pendientes</li>
     *     <li>3. Mostrar todas las comandas, pendientes y recogidas, dado un tramo</li>
     *     <li>4. Mostrar Carta</li>
     *     <li>5. Ver pedidos de un Alumno</li>
     * </ul>
     *
     * @return
     */
    static Integer menuConsulta() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Menu de Consulta");
        System.out.println("<<           >>");
        System.out.println("0. Salir al menu principal");
        System.out.println("1. Listar comandas pendientes de un dia");
        System.out.println("2. Mostrar todas las comandas pendientes");
        System.out.println("3. Mostrar todas las comandas, pendientes y recogidas, dado un tramo");
        System.out.println("4. Mostrar Carta");
        System.out.println("5. Mostrar los pedidos de un Alumno");
        clean(1);
        return sc.nextInt();

    }

    /**
     * Muestra el menu de las opciones de modificacion a la base de datos. Estas serian:
     * <ul>
     *     <li>0. Salir al menu principal</li>
     *     <li>1. Eliminar pedido</li>
     *     <li>2. Marcar pedido como recogido</li>
     *     <li>3. Modificar Pedido</li>
     *     <li>4. Modificar producto de la carta</li>
     * </ul>
     *
     * @return opcion seleccionada
     */
    static Integer menuMod() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Menu de Modificacion");
        System.out.println("<<                >>");
        System.out.println("0. Salir al menu principal");
        System.out.println("1. Eliminar Pedido / Producto");
        System.out.println("2. Marcar pedido como Recogido");
        System.out.println("3. Modificar Pedido");
        System.out.println("4. Modificar Producto de la carta");
        clean(1);
        return sc.nextInt();
    }

    /**
     * Muestra el menu de las opciones de insercion a la base de datos. Estas serian:
     * <ul>
     *     <li>0. Salir al menu principal</li>
     *     <li>1. Hacer pedido (Previamente muestra la carta)</li>
     *     <li>2. Insertar Nuevo Producto</li>
     *     <li>3. Rellenar Stock de un Producto existente</li>
     * </ul>
     *
     * @return opcion seleccionada
     */
    static Integer menuIngreso() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Menu de Ingreso");
        System.out.println("<<           >>");
        System.out.println("0. Salir al menu principal");
        System.out.println("1. Hacer Pedido");
        System.out.println("2. Insertar Nuevo Producto");
        System.out.println("3. Rellenar Stock de un Producto existente");
        clean(1);
        return sc.nextInt();
    }

    /**
     * Limpa la pantalla y muestra mensaje de despedida del programa
     */
    static void salir() {
        cleanDot(100);
        System.out.println("Gracias por Usarme");
    }

    /**
     * Realiza tantos saltos de linea como se desee
     *
     * @param max Numero de saltos de linea
     */
    static void clean(Integer max) {
        for (Integer i = 0; i < max; i++) {
            System.out.println("");
        }
    }

    /**
     * Realiza tantos saltos de linea como se desee, pero añadiendo un '.' en cada nueva linea
     *
     * @param max Numero de saltos de linea
     */
    static void cleanDot(Integer max) {
        for (Integer i = 0; i < max; i++) {
            System.out.println(".");
        }
    }

    /**
     * Lee un Integer introducido por teclado
     *
     * @return El valor recogido
     */
    static Integer leerInt() {
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    /**
     * Lee un String introducido por teclado
     *
     * @return El valor recogido
     */
    static String leerString() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    /**
     * Lee un Double introducido por teclado
     *
     * @return El valor recogido
     */
    static float leerFloat() {
        Scanner sc = new Scanner(System.in);
        float value = 0.0F;
        value = sc.nextFloat();
        return value;

    }

}