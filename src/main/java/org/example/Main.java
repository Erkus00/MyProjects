/**
 * Deberá permitir crear un nuevo pedido /
 * Deberá permitir eliminar un pedido existente /
 * Deberá poder marcar un pedido como recogido /
 * Deberá poder listar solo las comandas pendientes de hoy /
 * Deberá poder listar la carta disponible antes de crear la nueva comanda /
 * Deberá poder listar el total de pedidos asociados a un alumno. /
 * Deberá poder mostrar un resumen con algunos indicadores estadísticos que debéis definir
 * (por ejemplo ganancias del último mes, total de clientes, mejor cliente, total de pedidos en la última semana, producto más vendido,...).
 * La interacción con el usuario se realizará a través de un menú desde linea de comando. /
 */


package org.example;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class Main {
    public static Connection con;
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String PASS = "toorDam2";
    private static final String USER = "root";

    protected static Boolean primera_vez = true;

    static {

        String database = "comanda_desayunos";
        try {
            con = DriverManager.getConnection(URL + database, USER, PASS);
            System.out.println("Conexion realizada con exito a '" + database + "'");
        } catch (Exception e) {
            System.out.println("Problema al conectar con la base de Datos: " + database);
            System.out.println(e);
        } finally {
            System.out.println("Proceso de conexion terminado");
        }
    }

    public static void main(String[] args) {

        Integer opcion = menuInicio();
        boolean salir = false;

        while (!salir) {
            Integer elector = 0;
            Boolean salir_sub = false;
            cleanDot(5);

            switch (opcion) {
                case 0:
                    salir = true;
                    salir();
                    break;
                case 1:
                    elector = menuConsulta();

                    while (!salir_sub) {
                        cleanDot(3);
                        switch (elector) {
                            case 0:
                                salir_sub = true;
                                break;
                            case 1:
                                System.out.println("Listar Comandas pendientes de un dia");
                                break;
                            case 2:
                                System.out.println("Mostrar todas las comandas pendientes");
                                break;
                            case 3:
                                System.out.println("Mostrar comandas Pendientes");
                                break;
                            case 4:
                                System.out.println("Mostrar comandas de un tramo");
                                break;
                            case 5:
                                System.out.println("Ver pedidos de un Alumno");
                                break;
                            default:
                                clean(5);
                                System.out.println("Opcion no disponible");
                                clean(3);
                                break;
                        }
                    }
                    break;
                case 2:
                    elector = menuMod();
                    while (!salir_sub) {
                        cleanDot(3);
                        switch (elector) {
                            case 0:
                                salir_sub = true;
                                break;
                            case 1:
                                System.out.println("Eliminar Pedido");
                                break;
                            case 2:
                                System.out.println("Marcar pedido como recogido");
                                break;
                            case 3:
                                System.out.println("Modificar Pedido");
                                break;
                            case 4:
                                System.out.println("Modificar Producto");
                                break;
                            default:
                                clean(5);
                                System.out.println("Opcion no disponible");
                                clean(3);
                                break;
                        }
                    }
                    break;
                case 3:
                    elector = menuIngreso();
                    while (!salir_sub) {
                        clean(3);
                        switch (elector) {
                            case 0:
                                salir_sub = true;
                                break;
                            case 1:
                                System.out.println("Hacer Pedido");
                                break;
                            case 2:
                                gestionProducto();
                                break;
                            case 3:
                                System.out.println("Rellenar Stock");
                                break;
                            default:
                                clean(5);
                                System.out.println("Opcion no disponible");
                                clean(3);
                                break;
                        }
                    }
                    break;
                default:
                    clean(3);
                    System.out.println("Opcion no disponible");
                    break;
            }
            clean(10);
        }

    }

    // -------------------------------------------------------------------------------------------------------------------------------------------------------//
    //Funciones de gestion

    static void gestionProducto() {

        LinkedHashMap<Integer, LinkedHashMap<String, Object>> insercion_productos = new LinkedHashMap<>();
        Boolean salir = false;
        String nombre = "";
        Integer tipo = 0;
        float precio = 0.0F;
        Integer cantidad = 0;
        String correcto = "";
        Integer cont = 0;
        boolean insertado = false;

        while (!salir) {

            LinkedHashMap<String, Object> producto = new LinkedHashMap<>();

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

            System.out.println("Indique el numero de elementos que desea añadir, despues podrá modificarlos");
            cantidad = leerInt();

            System.out.println("Especificaciones del producto");
            System.out.println("Nombre: " + nombre);
            System.out.println("Tipo: " + leerTipo(tipo));
            System.out.println("Precio: " + precio + "€");
            System.out.println("Elementos añadidos: " + cantidad);

            System.out.println("¿Son correctos estos parámetros? [y/n]");
            correcto = leerString().toLowerCase();

            if (correcto == "y") {
                producto.put("nombre", nombre.toLowerCase());
                producto.put("tipo", leerTipo(tipo));
                producto.put("precio", precio);
                producto.put("cantidad", cantidad);
                cont++;
                insercion_productos.put(cont, producto);
            }
            insertado = insertarProducto(producto);

            if (insertado) {
                System.out.println("Producto insertado correctamente");
            } else {
                System.out.println("Producto no ha sido insertado");
            }

            System.out.println("¿Desea añadir otro producto[y/n]?");
            String otro = leerString().toLowerCase();

            if (otro != "y") {
                salir = true;
            }
        }
    }

    static String leerTipo(Integer tipo) {
        String tense = "";
        switch (tipo) {
            case 1:
                tense = "bebida";
                break;
            case 2:
                tense = "panaderia";
                break;
            case 3:
                tense = "bolleria";
                break;
            case 4:
                tense = "lacteo";
                break;
            case 5:
                tense = "otro";
                break;
            default:
                break;
        }
        return tense;
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
    static boolean insertarPedido() {
        Boolean finalizado = false;
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
    static Boolean insertarProducto(LinkedHashMap<String, Object> producto) {
        boolean finalizado = false;
        String sql_query = "INSERT INTO carta (nombre,tipo,precio,disponibilidad) VALUES (?,?,?,?);";
        try (PreparedStatement pst = con.prepareStatement(sql_query);) {
            pst.setString(1, producto.get("nombre").toString());
            pst.setString(2, producto.get("tipo").toString());
            pst.setFloat(3, (float) producto.get("precio"));

            if ((Integer) producto.get("cantidad") > 0) {
                pst.setBoolean(3, true);
            } else {
                pst.setBoolean(3, false);
            }
            pst.executeUpdate();
            finalizado = true;
        } catch (Exception e) {
            System.out.println(e);
            finalizado = false;
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
    static boolean eliminarPedido() {
        boolean finalizado = false;
        return finalizado;
    }

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
//    static ArrayList<String> listarProductos() {
//
//    }

    /**
     * Lista aquellos productos que están disponibles y su precio
     *
     * @return Hashmap con:
     * <ul>
     *     <li><b>Key: </b>Nombre del Producto</li>
     *     <li><b>Value: </b>Valor del Producto</li>
     * </ul>
     */
//    static HashMap<String, Integer> carta() {
//    }

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
//    static String infoProducto(Integer id_producto) {
//
//    }


    /**
     * Obtiene la disponibilidad de un producto de la carta
     *
     * @param id_producto Identificador del producto del que se desea obtener la disponibilidad
     * @return <ul>
     * <li><i>True</i>: Si el producto se encuentra disponible</li>
     * <li><i>False</i>: Si el producto no está disponible</li>
     * </ul>
     */
//    static Boolean obtenerDisponibilidadProducto(Integer id_producto) {
//
//    }

    /**
     * Muestra toda la informacion de una comanda sin importar su estado
     *
     * @param id_pedido Identificador del pedido
     * @return Muestra toda la informacion de un pedido:
     * <ul>
     *     <li>Fecha</li>
     *     <li>Cliente</li>
     *     <li>Estado</li>
     *     <li>Productos comprados</li>
     *     <li>Precio total del pedido</li>
     * </ul>
     */
//    static String infoPedido(Integer id_pedido) {
//
//    }

    /**
     * Muestra todas las comandas hechas desde el principio:
     *
     * @return Hashmap con:
     * <ul>
     *     <li><b>Key: </b> Id del Pedido</li>
     *     <li><b>Value: </b><ul>
     *     <li>Fecha</li>
     *     <li>Cliente</li>
     *     <li>Estado</li>
     *     <li>Productos comprados</li>
     *     <li>Precio total del pedido</li>
     * </ul></li>
     * </ul>
     */

//    static HashMap<Integer, String> listarPedidos() {
//
//    }

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
        clean(1);
        System.out.println("Indique que desea hacer:");
        clean(2);
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
        clean(2);
        System.out.println("0. Salir al menu principal");
        System.out.println("1. Listar comandas pendientes de un dia");
        System.out.println("2. Mostrar todas las comandas pendientes");
        System.out.println("3. Mostrar todas las comandas, pendientes y recogidas, dado un tramo");
        System.out.println("4. Mostrar Carta");
        System.out.println("5. Mostrar los pedidos de un Alumno");
        System.out.println();
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
        clean(2);
        System.out.println("0. Salir al menu principal");
        System.out.println("1. Eliminar Pedido");
        System.out.println("2. Marcar pedido como Recogido");
        System.out.println("3. Modificar Pedido");
        System.out.println("4. Modificar Producto de la carta");
        System.out.println();
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
        clean(2);
        System.out.println("0. Salir al menu principal");
        System.out.println("1. Hacer Pedido");
        System.out.println("2. Insertar Nuevo Producto");
        System.out.println("3. Rellenar Stock de un Producto existente");
        System.out.println();
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