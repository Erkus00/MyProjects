package Model;

public class Producto {

    private Integer id;
    private String nombre;
    private String tipo;
    private float precio;
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

    public static String leerTipo(Integer tipo) {
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

    public String cartaView() {
        return id + "|| " + nombre + " --> " + precio + " €";
    }


}
