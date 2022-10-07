package Model;

public class Cliente {
    private String nombre;
    private Integer identificacion;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(Integer identificacion) {
        this.identificacion = identificacion;
    }

    @Override
    public String toString() {
        return
                "Nombre del Cliente -> '" + nombre + '\'' +
                        ", identificacion -> " + identificacion;
    }
}
