package model;

import static controller.ProductoDAO.leerTipo;

/**
 * Objeto que guarda las opciones dentro de la carta. Así, el usuario puede seleccionarla y pedirla para su disfrute
 */
public class Producto {

    // Identificador del producto. Generado y Gestionado por SGBD
    private Integer id;

    // Nombre descriptivo del Producto
    private String nombre;

    // Tipo de Producto (Clasificacion)
    private String tipo;

    // Precio en € del producto
    private float precio;

    // Indica la disponibilidad del producto. TRUE: Disponible; FALSE: No Disponible
    private boolean disponible;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = leerTipo(tipo);
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {

        return "id -> " + id +
                ", nombre -> '" + nombre + '\'' +
                ", tipo -> '" + tipo + '\'' +
                ", precio -> " + precio +
                ", disponible -> " + disponible;
    }

    /**
     * Formato User-Friendly para poder mostrar un producto al usuario
     *
     * @return La id del producto, el nombre y el precio guardados
     */
    public String cartaView() {
        return id + " || " + nombre + " --> " + precio + " €";
    }


}
