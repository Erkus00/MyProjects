package controller;

import entity.CartaEntity;

import static dao.CartaDAO.insertarProducto;
import static view.View.*;

public class ProductoController {
    /**
     * Funcion que permite gestionar la insercion de un producto en la Base de Datos
     * Guia al usuario y crea un producto a partir de los datos introducidos. Posteriormente lo inserta en la base de Datos
     */
    public static void insercionProducto() throws Exception {

        boolean salir = false;
        String nombre = "";
        Integer tipo = 0;
        float precio = 0.0F;
        String correcto = "";
        Integer insertado;

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


            CartaEntity producto = new CartaEntity();
            if (correcto.equals("y")) {
                producto.setNombre(nombre.toUpperCase());
                producto.setTipo(leerTipo(tipo));
                producto.setPrecio(precio);
                producto.setDisponibilidad((byte) 1);
                insertado = insertarProducto(producto);
            } else {
                insertado = 0;
            }
            clean(3);
            if (insertado!=0) {
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
