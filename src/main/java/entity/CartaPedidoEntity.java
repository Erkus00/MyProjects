package entity;

import javax.persistence.*;

@Entity
@Table(name = "carta_pedido", schema = "comanda_desayunos", catalog = "")
@IdClass(CartaPedidoEntityPK.class)
public class CartaPedidoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_producto", nullable = false)
    private int idProducto;
    @Basic
    @Column(name = "cantidad", nullable = false)
    private int cantidad;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_pedido", nullable = false)
    private int idPedido;
    @ManyToOne
    @JoinColumn(name = "id_producto", referencedColumnName = "id", nullable = false)
    private CartaEntity cartaByIdProducto;
    @ManyToOne
    @JoinColumn(name = "id_pedido", referencedColumnName = "id", nullable = false)
    private PedidoEntity pedidoByIdPedido;

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public CartaEntity getCartaByIdProducto() {
        return cartaByIdProducto;
    }

    public void setCartaByIdProducto(CartaEntity cartaByIdProducto) {
        this.cartaByIdProducto = cartaByIdProducto;
    }

    public PedidoEntity getPedidoByIdPedido() {
        return pedidoByIdPedido;
    }

    public void setPedidoByIdPedido(PedidoEntity pedidoByIdPedido) {
        this.pedidoByIdPedido = pedidoByIdPedido;
    }
}
