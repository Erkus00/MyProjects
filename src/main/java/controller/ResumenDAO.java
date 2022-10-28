package controller;

//import model.Pedido;
//import model.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.util.ArrayList;
import java.util.Date;

public class ResumenDAO {

    private static final Connection con = Conexion.getCon();
//    ArrayList<Pedido> all_pedidos = new ArrayList<>();
//    ArrayList<Producto> all_productos = new ArrayList<>();

    public static void info() {
        System.out.println("Informacion Estadistica: ");
        System.out.println("------------------------------------------------------------");
        System.out.println("Total de Clientes: " + total_clientes().toString());
        System.out.println("Total Pedidos de la Ultima Semana: " + total_pedidos_ultima_semana().toString());
        System.out.println("------------------------------------------------------------");
    }

    public static Integer total_clientes() {
        int total_clientes = 0;
        String sql_query = "SELECT COUNT(DISTINCT `cliente`) AS x FROM pedido";
        try (PreparedStatement pst = con.prepareStatement(sql_query)) {
            ResultSet rst = pst.executeQuery();
            while (rst.next()) {
                total_clientes = rst.getInt("x");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return total_clientes;
    }

    public static Integer total_pedidos_ultima_semana() {
        int total_pedidos = 0;
        String sql_query = "SELECT COUNT(DISTINCT `identificador`) AS x FROM pedido WHERE fecha>= DATE_ADD(?, INTERVAL -3 DAY);";
        try (PreparedStatement pst = con.prepareStatement(sql_query)) {
            Date now = new Date();
            java.sql.Date sqlDate_now = new java.sql.Date(now.getTime());
            pst.setDate(1, sqlDate_now);
            ResultSet rst = pst.executeQuery();
            while (rst.next()) {
                total_pedidos = rst.getInt("x");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return total_pedidos;
    }

//
//    public void all_pedidos() {
//        String sql_query = "SELECT DISTINCT identificador FROM pedido;";
//        try (PreparedStatement pst = con.prepareStatement(sql_query)) {
//            ResultSet rst = pst.executeQuery();
//            while (rst.next()) {
//                Pedido pedido = PedidoDAO.infoPedido(rst.getInt("identificador"));
//                all_pedidos.add(pedido);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void all_productos() {
//        String sql_query = "SELECT DISTINCT id FROM producto;";
//        try (PreparedStatement pst = con.prepareStatement(sql_query)) {
//            ResultSet rst = pst.executeQuery();
//            while (rst.next()) {
//                Producto producto = ProductoDAO.infoProducto(rst.getInt("id"));
//                all_productos.add(producto);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }


}
