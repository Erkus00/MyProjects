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

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

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
        Boolean salir = false;

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
                                break;
                            case 2:
                                break;
                            case 3:
                                break;
                            case 4:
                                break;
                            case 5:
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
                                break;
                            case 2:
                                break;
                            case 3:
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
                        cleanDot(3);
                        switch (elector) {
                            case 0:
                                salir_sub = true;
                                break;
                            case 1:
                                break;
                            case 2:
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
    // Funciones CRUD (Create, Read, Delete and Update

    // Funciones de crear Datos (Create)

    /**
     * Permite crear un nuevo Pedido siempre que el producto se encuentre disponible
     *
     * @return <ul>
     * <li><i>True</i>: Si se ha podido Crear</li>
     * <li><i>False</i>: Si ha habido un error a la hora de crear el pedido</li>
     * </ul>
     */
    static Boolean crearPedido() {
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
    static Boolean crearProducto() {

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
    static Boolean eliminarPedido() {
        Boolean finalizado = false;
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
    static Boolean marcarPedidoRecogido(Integer id_pedido) {
        Boolean finalizado = false;
        return finalizado;
    }


    // Funciones de Leer/Coger de Datos (Read)


    /**
     * Genera una lista con todos los productos esten o no disponibles
     *
     * @return Listado de la informacion detallada de todos los productos
     */
    static ArrayList<String> listarProductos() {

    }

    /**
     * Lista aquellos productos que están disponibles y su precio
     *
     * @return Hashmap con:
     * <ul>
     *     <li><b>Key: </b>Nombre del Producto</li>
     *     <li><b>Value: </b>Valor del Producto</li>
     * </ul>
     */
    static HashMap<String, Integer> carta() {
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
    static String infoProducto(Integer id_producto) {

    }


    /**
     * Obtiene la disponibilidad de un producto de la carta
     *
     * @param id_producto Identificador del producto del que se desea obtener la disponibilidad
     * @return <ul>
     * <li><i>True</i>: Si el producto se encuentra disponible</li>
     * <li><i>False</i>: Si el producto no está disponible</li>
     * </ul>
     */
    static Boolean obtenerDisponibilidadProducto(Integer id_producto) {

    }

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
    static String infoPedido(Integer id_pedido) {

    }

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

    static HashMap<Integer, String> listarPedidos() {

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
    static HashMap<Integer, String> listarPedidos(Date fecha) {

    }


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
    static HashMap<Integer, Date> pedidosAlumno(String nombre_alumno) {
    }

    /**
     * Accede a la id de todos los pedidos que se encuentran en estado: Pendiente
     *
     * @return Listado de la id de todos aquellos pedidos que se encuentran pendientes
     */
    static ArrayList<Integer> pedidosPendientes() {

    }

    /**
     * Accede a la id de todos los pedidos que se encuentran en estado: Pendiente de una fecha en concreto
     *
     * @param fecha Fecha de la que se desea obtener el listado
     * @return Listado de la id de todos aquellos pedidos que se encuentran pendientes.
     */
    static ArrayList<Integer> pedidosPendientes(Date fecha) {

    }

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
        if (primera_vez) {
            System.out.println("BIENVENIDO AL PROGRAMA DE GESTION DE LOS DESAYUNOS DEL CESUR");
            primera_vez = false;

        }
        clean(10);
        System.out.println("<<                                                        >>");
        System.out.println("Indique que desea hacer:");
        clean(2);
        System.out.println("0. Salir del programa");
        System.out.println("1. Consultar Datos");
        System.out.println("2. Modificar Datos");
        System.out.println("3. Ingresar Datos");
        clean(3);
        Integer eleccion = sc.nextInt();
        sc.close();
        return eleccion;

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
        cleanDot(2);
        System.out.println("0. Salir al menu principal");
        System.out.println("1. Listar comandas pendientes de un dia");
        System.out.println("2. Mostrar todas las comandas pendientes");
        System.out.println("3. Mostrar todas las comandas, pendientes y recogidas, dado un tramo");
        System.out.println("4. Mostrar Carta");
        System.out.println("5. Mostrar los pedidos de un Alumno");
        Integer opcion = sc.nextInt();
        sc.close();
        return opcion;

    }

    /**
     * Muestra el menu de las opciones de modificacion a la base de datos. Estas serian:
     * <ul>
     *     <li>0. Salir al menu principal</li>
     *     <li>1. Eliminar pedido</li>
     *     <li>2. Marcar pedido como recogido</li>
     *     <li>3. Modificar Pedido</li>
     * </ul>
     *
     * @return opcion seleccionada
     */
    static Integer menuMod() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Menu de Modificacion");
        System.out.println("<<                >>");
        cleanDot(2);
        System.out.println("0. Salir al menu principal");
        System.out.println("1. Eliminar Pedido");
        System.out.println("2. Marcar pedido como Recogido");
        System.out.println("3. Modificar Pedido");
        Integer opcion = sc.nextInt();
        sc.close();
        return opcion;
    }

    /**
     * Muestra el menu de las opciones de insercion a la base de datos. Estas serian:
     * <ul>
     *     <li>0. Salir al menu principal</li>
     *     <li>1. Hacer pedido (Previamente muestra la carta)</li>
     *     <li>2. Insertar Producto</li>
     * </ul>
     *
     * @return opcion seleccionada
     */
    static Integer menuIngreso() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Menu de Ingreso");
        System.out.println("<<           >>");
        cleanDot(2);
        System.out.println("0. Salir al menu principal");
        System.out.println("1. Hacer Pedido");
        System.out.println("2. Insertar Producto");
        Integer opcion = sc.nextInt();
        sc.close();
        return opcion;
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
}