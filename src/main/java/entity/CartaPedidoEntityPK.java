package entity;

import java.io.Serializable;
import java.util.Objects;

public class CartaPedidoEntityPK implements Serializable {
    private int idProducto;
    private int idPedido;

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartaPedidoEntityPK that = (CartaPedidoEntityPK) o;
        return idProducto == that.idProducto && idPedido == that.idPedido;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProducto, idPedido);
    }
}
