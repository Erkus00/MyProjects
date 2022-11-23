package model;

import entity.CartaPedidoEntity;
import entity.PedidoEntity;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import static dao.CartaDAO.infoProducto;
import static view.View.clean;
import static view.View.cleanDot;

public class Pedido {
    private PedidoEntity info_pedido;
    private ArrayList<CartaPedidoEntity> info_productos;

    public Pedido(PedidoEntity pedido, ArrayList<CartaPedidoEntity> info_productos) {
        if(pedido!=null && info_productos!=null){
            this.info_pedido = pedido;
            this.info_productos = info_productos;
        }
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
        cleanDot(2);
        info_productos.forEach((k) -> {
            System.out.println(infoProducto(k.getIdProducto()).cartaView() + " º | º Cantidad: " + k.getCantidad());
        });
        clean(2);
        System.out.println(" \t Estado: "+info_pedido.getEstado());

        return "\n\t Cliente: " + info_pedido.getCliente() + "\n|| Fecha: " + info_pedido.getFecha() + "\n " + "----------------------------------------------------------------------";
    }

    /**
     * Vista que simula un recibo emitido tras la efectuacion del pedido. Tambien calcula el precio total del pedido a partir de la cantidad deseada
     */
    public void recibo() {
        AtomicReference<Float> precio_total = new AtomicReference<>((float) 0);
        cleanDot(3);
        System.out.println("**********************************");
        System.out.println("Nombre: " + info_pedido.getCliente());
        System.out.println("Fecha: " + info_pedido.getFecha());
        System.out.println();
        System.out.println("Productos: ");
        info_productos.forEach((k) -> {
            if (k.getCantidad() > 0) {
                System.out.println(infoProducto(k.getIdProducto()).cartaView() + " -- Uds: " + k.getCantidad());
                float precio = (float) infoProducto(k.getIdProducto()).getPrecio() * k.getCantidad();
                precio_total.updateAndGet(v1 -> (v1 + precio));
            }
        });
        System.out.println();
        System.out.println("Precio Final a Pagar: " + precio_total + "€");
        System.out.println("**********************************");
        cleanDot(3);
    }
}
