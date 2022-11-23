package controller;

import model.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import static view.View.*;

/**
 * Controlador de Producto
 */
public class ProductoDAO {

    // Conexion con la Base de Datos
    protected static final Connection con = Conexion.getCon();

    /**
     * Devuelve un producto de la Base de Datos con la 'Id' indicada
     *
     * @param id_producto Id unico del producto
     * @return Objeto Pedido con la informacion guardada en la base de Datos
     */
    public static Producto infoProducto(Integer id_producto) {
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
     * Permite guardar un producto en la base de Datos
     *
     * @param producto Objeto de la clase Producto que se desea guardar en la Base de Datos
     * @return <ul>
     * <li><i>True</i>: Si se ha podido Crear</li>
     * <li><i>False</i>: Si ha habido algun error</li>
     * </ul>
     */
    static boolean insertarProducto(Producto producto) {
        boolean finalizado = false;

        String sql_query = "INSERT INTO carta (nombre,tipo,precio,disponibilidad) VALUES (?,?,?,?);";
        try (PreparedStatement pst = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);) {
            pst.setString(1, producto.getNombre());
            pst.setString(2, producto.getTipo());
            pst.setFloat(3, producto.getPrecio());
            pst.setBoolean(4, producto.isDisponible());
            int i = pst.executeUpdate();
            if (i > 0) {
                finalizado = true;
            } else {
                System.out.println("Algo ha fallado al insertar el Producto");
            }

            ResultSet generatedKeys = pst.getGeneratedKeys();
            if (generatedKeys.next()) {
                Integer id = generatedKeys.getInt(1);
                producto.setId(id);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return finalizado;
    }

    /**
     * Funcion que permite gestionar la insercion de un producto en la Base de Datos
     * Guia al usuario y crea un producto a partir de los datos introducidos. Posteriormente lo inserta en la base de Datos
     */
    public static void insercionProducto() {

        boolean salir = false;
        String nombre = "";
        Integer tipo = 0;
        float precio = 0.0F;
        String correcto = "";
        boolean insertado = false;

        while (!salir) {

            System.out.println("Indique el nombre del producto");
            nombre = leerString().toUpperCase();

            System.out.println("Indique el Tipo");
            clean(1);
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
            System.out.println("Tipo: " + leerTipo(tipo));
            System.out.println("Precio: " + precio + "€");

            clean(1);
            System.out.println("¿Son correctos los datos del producto?  [y/n]");
            cleanDot(1);
            System.out.println("y -> Sí");
            System.out.println("n -> No");
            correcto = leerString().toLowerCase();
            clean(1);


            Producto producto = new Producto();
            if (correcto.equals("y")) {
                producto.setNombre(nombre.toUpperCase());
                producto.setTipo(tipo);
                producto.setPrecio(precio);
                producto.setDisponible(true);
                insertado = insertarProducto(producto);
            } else {
                insertado = false;
            }
            clean(3);
            if (insertado) {
                System.out.println("Producto insertado correctamente");
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
     * Lista aquellos productos que están disponibles
     *
     * @return Listado de los productos que cumplen la condicion de estar disponibles
     */
    public static ArrayList<Producto> carta() {
        ArrayList<Producto> carta = new ArrayList<>();
        Producto prod;

        String sql_query = "SELECT id FROM comanda_desayunos.carta WHERE disponibilidad=1;";
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
     * Funcion para establecer el Tipo de producto
     *
     * @param tipo Valor del 1 al 5, lo que facilita la normalizacion dentro de la tabla
     * @return El tipo que se guardará dentro de la Base de Datos
     */
    public static String leerTipo(Integer tipo) {
        String tense = "";
        switch (tipo) {
            case 1 -> tense = "BEBIDA";
            case 2 -> tense = "PANADERIA";
            case 3 -> tense = "BOLLERIA";
            case 4 -> tense = "LACTEO";
            case 5 -> tense = "OTRO";
            default -> {
            }
        }
        return tense;
    }
}
