package entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "carta", schema = "comanda_desayunos", catalog = "")
public class CartaEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "nombre", nullable = false, length = 255)
    private String nombre;
    @Basic
    @Column(name = "tipo", nullable = false, length = 255)
    private String tipo;
    @Basic
    @Column(name = "precio", nullable = false, precision = 0)
    private double precio;
    @Basic
    @Column(name = "disponibilidad", nullable = false)
    private byte disponibilidad;
    @OneToMany(mappedBy = "cartaByIdProducto")
    private Collection<CartaPedidoEntity> cartaPedidosById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public byte getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(byte disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public Collection<CartaPedidoEntity> getCartaPedidosById() {
        return cartaPedidosById;
    }

    public void setCartaPedidosById(Collection<CartaPedidoEntity> cartaPedidosById) {
        this.cartaPedidosById = cartaPedidosById;
    }

    @Override
    public String toString() {
        return "id -> " + id +
                ", nombre -> '" + nombre + '\'' +
                ", tipo -> '" + tipo + '\'' +
                ", precio -> " + precio +
                ", disponible -> " + disponibilidad;
    }

    public String cartaView() {
        return id + " || " + nombre + " --> " + precio + " €";
    }
}
