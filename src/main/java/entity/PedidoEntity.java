package entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "pedido", schema = "comanda_desayunos")
public class PedidoEntity {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "fecha", nullable = false)
    private Date fecha;
    @Basic
    @Column(name = "cliente", nullable = false, length = -1)
    private String cliente;
    @Basic
    @Column(name = "estado", nullable = false, length = 255)
    private String estado;
//    @OneToMany(mappedBy = "pedidoByIdPedido")
//    private Collection<CartaPedidoEntity> cartaPedidosById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

//    public Collection<CartaPedidoEntity> getCartaPedidosById() {
//        return cartaPedidosById;
//    }
//
//    public void setCartaPedidosById(Collection<CartaPedidoEntity> cartaPedidosById) {
//        this.cartaPedidosById = cartaPedidosById;
//    }
}
