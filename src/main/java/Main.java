import dao.CartaDAO;
import dao.PedidoDAO;
import entity.CartaEntity;
import entity.PedidoEntity;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;

public class Main {

    public static void main(final String[] args) throws Exception {

        //ProductoDAO------------------------------------------------
        // Carta
        ArrayList<CartaEntity> carta = CartaDAO.carta();
        for (CartaEntity e : carta) {
            System.out.println(e.cartaView());
        }

//         Insertar Producto
//        CartaEntity producto_nuevo = new CartaEntity();
//        producto_nuevo.setNombre("Agua");
//        producto_nuevo.setTipo("Bebida");
//        producto_nuevo.setPrecio(2.0F);
//        producto_nuevo.setDisponibilidad((byte) 1);
//        Integer id_insertado = CartaDAO.insertarProducto(producto_nuevo);
//        System.out.println("Producto Insertado con id: " + id_insertado);
//        System.out.println(producto_nuevo.getId());
//
//        // Carta
//        carta = CartaDAO.carta();
//        for (CartaEntity e : carta) {
//            System.out.println(e.cartaView());
//        }
//
//        // Eliminar Producto. Cuidado ya que no se puede eliminar si existe un pedido con este producto. Lo mejor seria convertirlo en no Disponible
//        boolean eliminado = CartaDAO.eliminarProducto(producto_nuevo.getId());
//        System.out.println("Se ha eliminado el Producto "+producto_nuevo.getId()+"?:" + eliminado);
//
        // Carta
//        carta = CartaDAO.carta();
//        for (CartaEntity e : carta) {
//            System.out.println(e.cartaView());
//        }
//
//        // Info de un producto
//        CartaEntity producto = CartaDAO.infoProducto(3);
//        System.out.println(producto.cartaView());

//
//        // PedidoDAO-----------------------------------

        PedidoEntity new_pedido = new PedidoEntity();
        // Mostrar todos los pedidos Pendientes
        System.out.println("Pedidos Pendientes: ");
        ArrayList<PedidoEntity> lista_pedidos = PedidoDAO.listarAllPedidos(true);
        lista_pedidos.forEach(k -> {
            System.out.println(k.toString());
        });
//        // Mostrar todos los pedidos
//        System.out.println("All Pedidos: ");
//        lista_pedidos = PedidoDAO.listarAllPedidos(false);
//        lista_pedidos.forEach(k -> {
//            System.out.println(k.toString());
//        });
    }
}