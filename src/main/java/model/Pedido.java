package model;

import entity.CartaEntity;
import entity.CartaPedidoEntity;
import entity.PedidoEntity;

import java.util.ArrayList;

public class Pedido {
    private PedidoEntity info_pedido;
    private ArrayList<CartaPedidoEntity> info_productos;

    public Pedido(PedidoEntity pedido, ArrayList<CartaPedidoEntity> info_productos){
        this.info_pedido=pedido;
        this.info_productos=info_productos;
    }
    public PedidoEntity getInfo_pedido() {
        return info_pedido;
    }

    public void setInfo_pedido(PedidoEntity info_pedido) {
        this.info_pedido = info_pedido;
    }

    public ArrayList<CartaPedidoEntity> getInfo_productos() {
        return info_productos;
    }

    public void setInfo_productos(ArrayList<CartaPedidoEntity> info_productos) {
        this.info_productos = info_productos;
    }

    /**
     * Vista mas sencilla de interpretar por el usuario
     *
     * @return String con la Identificacion, el nombre del pedido y la fecha
     */
    public String infoView() {
        System.out.println(">>-------------------------->>");
        System.out.println("Identificacion del pedido --> " + info_pedido.getId() + " :>");
        System.out.println("----------------------------------------------------------------------");
        info_productos.forEach((k) -> {
            System.out.println(k.getCartaByIdProducto().cartaView() + " º | º Cantidad: " + k.getCantidad());
        });

        return "\n Cliente: " + info_pedido.getCliente() + "\n || Fecha: " + info_pedido.getFecha() + "\n"+"----------------------------------------------------------------------";
    }
}
