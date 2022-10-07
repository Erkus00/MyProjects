package Controller;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {


    private static Connection con;
    private static final String URL = "jdbc:mysql://localhost:3307/";
    private static final String PASS = "Erkus00";
    private static final String USSER = "root";

    static {

        String database = "comanda_desayunos";
        try {
            con = DriverManager.getConnection(URL + database, USSER, PASS);
            System.out.println("Conexion realizada con exito a '" + database + "'");
        } catch (Exception e) {
            System.out.println("Problema al conectar con la base de Datos: " + database);
            System.out.println(e);
        } finally {
            System.out.println("Proceso de conexion terminado");
        }
    }

    public static Connection getCon() {
        return con;
    }
}
