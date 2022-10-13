package Model;

import java.sql.Date;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class Pedido {

    private Integer id;
    private Integer identificacion;
    private Date fecha;
    private String cliente;
    private String estado;

    private HashMap<Producto, Integer> productos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(Integer identificacion) {
        this.identificacion = identificacion;
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

    public HashMap<Producto, Integer> getProductos() {
        return productos;
    }

    public void setProductos(HashMap<Producto, Integer> producto) {
        this.productos = producto;
    }


    @Override
    public String toString() {
        return "fecha -> " + fecha +
                ", cliente -> " + cliente +
                ", estado -> '" + estado + '\'' +
                ", productos: {" + productos +
                '}';
    }

    public String infoView() {
        return identificacion + " || " + cliente + " || " + fecha + " || " + productos;
    }

    public void recibo() {
        AtomicReference<Float> precio_total = new AtomicReference<>((float) 0);

        System.out.println("Nombre: " + cliente);
        System.out.println("Fecha: " + fecha);
        System.out.println();
        System.out.println("Productos: ");
        productos.forEach((k, v) -> {
            if (v > 0) {
                System.out.println(k.cartaView() + " -- Uds: " + v);
                float precio = k.getPrecio() * v;
                precio_total.updateAndGet(v1 -> (v1 + precio));
            }
        });
        System.out.println();
        System.out.println("Precio Final a Pagar: " + precio_total + "€");


    }
}
