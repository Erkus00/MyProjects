package org.example;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.util.HashMap;

public class Main {

    public static Connection con;
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String PASS = "toorDam2";
    private static final String USER = "root";
    private static String database = "comanda_desayunos";

    static {

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

    // Funciones CRUD (Create, Read, Delete and Update


    // Funciones de crear Datos (Create)

    /**
     * Permite crear un nuevo Pedido
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
     * Permite eliminar un pedido
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
    static Boolean marcarPedidoRecogido(Integer id_producto) {
        Boolean finalizado = false;
        return finalizado;
    }


    // Funciones de Leer/Coger de Datos (Read)

    /**
     * Genera una lista con todos los productos esten o no disponibles
     *
     * @return Hashmap con:
     * <ul>
     *     <li><b>Key: </b> Id de producto</li>
     *     <li><b>Value: </b> Nombre del producto</li>
     *
     * </ul>
     */
    static HashMap<Integer, String> listarProductos() {

    }

    /**
     * Obtiene la disponibilidad de un producto
     *
     * @param id_producto - Identificador del producto del que se desea obtener la disponibilidad
     * @return <ul>
     * * <li><i>True</i>: Si el producto se encuentra disponible</li>
     * * <li><i>False</i>: Si el producto no está disponible</li>
     * * </ul>
     */
    static Boolean obtenerDisponibilidad(Integer id_producto) {

    }

    /**
     * Muestra todas las comandas hechas desde el principio:
     * <ul>
     *     <li>Nombre del producto</li>
     *     <li>Fecha de comanda</li>
     *     <li>Nombre del Alumno</li>
     * </ul>
     */
    static void listarComandas() {

    }

    /**
     * Muestra todas las comandas de una fecha
     *
     * @param fecha - Fecha de la que se desean obtener las comandas
     * @return Hashmap con:
     * <ul>
     *     <li><b>Key: </b> Id de pedido</li>
     *     <li><b>Value: </b> Nombre del producto</li>
     *
     * </ul>
     */
    static HashMap<Integer, String> listarComandas(Date fecha) {

    }

    static HashMap<String, Integer> carta() {
    }

    static HashMap<String, Date> pedidos(Integer id_alumno) {

    }

    // Funciones Auxiliares
    static void menu() {

    }
}