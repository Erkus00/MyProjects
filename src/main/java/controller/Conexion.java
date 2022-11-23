package controller;

import java.sql.Connection;
import java.sql.DriverManager;

import static view.View.cleanDot;

/**
 * Clase para configurar la conexion con la Base de Datos
 */
public class Conexion {


    private static final Connection con;
    private static final String URL = "jdbc:mysql://localhost:3306/";
    // Contraseña
    private static final String PASS = "";
    // Usuario
    private static final String USER = "root";

    static {
        // Nombre de la Base de Datos
        String database = "comanda_desayunos";
        cleanDot(5);
        try {
            con = DriverManager.getConnection(URL + database, USER, PASS);
            System.out.println("Conexion realizada con exito a '" + database + "'");
        } catch (Exception e) {
            System.out.println("Problema al conectar con la base de Datos: " + database);
            throw new RuntimeException(e);
        } finally {
            System.out.println("Proceso de conexion terminado");
        }
        cleanDot(4);
    }

    public static Connection getCon() {
        return con;
    }
}
