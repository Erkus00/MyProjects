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

    }

    // -------------------------------------------------------------
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
     * @return Lista con la informacion de todos los pedidos. De cada Pedido:
     * <ul>
     *     <li>Fecha</li>
     *     <li>Cliente</li>
     *     <li>Estado</li>
     *     <li>Productos comprados</li>
     *     <li>Precio total del pedido</li>
     * </ul>
     */

    static ArrayList<String> listarPedidos() {

    }

    /**
     * Muestra todas las comandas de una fecha en concreto
     *
     * @param fecha Fecha de la cual se desean obtener las comandas
     * @return Lista la id  de todos los pedidos de una fecha en concreto. De cada Pedido:
     * <ul>
     *     <li>Cliente</li>
     *     <li>Estado</li>
     *     <li>Productos comprados</li>
     *     <li>Precio total del pedido</li>
     * </ul>
     */
    static ArrayList<String> listarPedidos(Date fecha) {

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
    static HashMap<Integer, Date> pedidos(String nombre_alumno) {
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
     * <ol>
     *     <li>Consultar Datos</li>
     *     <li>Modificar Datos</li>
     *     <li>Ingresar Datos</li>
     * </ol>
     *
     * @return La opcion seleccionada (1, 2, 3 ó salir)
     */
    static Integer menuInicio() {
        Scanner sc = new Scanner(System.in);
        if (primera_vez) {
            System.out.println("BIENVENIDO AL PROGRAMA DE GESTION DE LOS DESAYUNOS DEL CESUR");
            primera_vez = false;

        }
        clean(50);
        System.out.println("<<                                                        >>");
        System.out.println("Indique que desea hacer:");
        clean(2);
        System.out.println("0. Salir");
        System.out.println("1. Consultar Datos");
        System.out.println("2. Modificar Datos");
        System.out.println("3. Ingresar Datos");
        clean(3);
        Integer eleccion = sc.nextInt();
        sc.close();
        return eleccion;

    }

    static Integer menuConsulta() {

    }

    static Integer menuMod() {

    }

    static Integer menuIngreso() {

    }

    static void salir() {
        cleanDot(100);
        System.out.println("Gracias por Usarme");
        System.out.println("=UwU=");
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