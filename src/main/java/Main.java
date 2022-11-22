//import dao.CartaDAO;
//import dao.CartaPedidoDAO;
//import dao.PedidoDAO;
//import entity.CartaEntity;
//import entity.CartaPedidoEntity;
//import entity.PedidoEntity;
//
//import javax.persistence.criteria.CriteriaBuilder;
//import java.util.ArrayList;
//import java.util.Date;
//
//public class Main {
//
//    public static void main(final String[] args) throws Exception {
////
////        //ProductoDAO------------------------------------------------
////        // Carta
////        ArrayList<CartaEntity> carta = CartaDAO.carta();
////        for (CartaEntity e : carta) {
////            System.out.println(e.cartaView());
////        }
////
////         Insertar Producto
//        CartaEntity producto_nuevo = new CartaEntity();
//        producto_nuevo.setNombre("Agua");
//        producto_nuevo.setTipo("Bebida");
//        producto_nuevo.setPrecio(2.0F);
//        producto_nuevo.setDisponibilidad((byte) 1);
//        Integer id_insertado = CartaDAO.insertarProducto(producto_nuevo);
//        System.out.println("Producto Insertado con id: " + id_insertado);
//        System.out.println(producto_nuevo.getId());
//////
//////        // Carta
//////        carta = CartaDAO.carta();
//////        for (CartaEntity e : carta) {
//////            System.out.println(e.cartaView());
//////        }
//////
//////        // Eliminar Producto. Cuidado ya que no se puede eliminar si existe un pedido con este producto. Lo mejor seria convertirlo en no Disponible
//////        boolean eliminado = CartaDAO.eliminarProducto(producto_nuevo.getId());
//////        System.out.println("Se ha eliminado el Producto "+producto_nuevo.getId()+"?:" + eliminado);
//////
////        // Carta
//////        carta = CartaDAO.carta();
//////        for (CartaEntity e : carta) {
//////            System.out.println(e.cartaView());
//////        }
//////
//////        // Info de un producto
//////        CartaEntity producto = CartaDAO.infoProducto(3);
//////        System.out.println(producto.cartaView());
////
//////
//////        // PedidoDAO-----------------------------------
////        // Creacion del Pedido
////        // Insertar pedido
////        Date now = new Date();
////        java.sql.Date sqlDate = new java.sql.Date(now.getTime());
////
////        PedidoEntity new_pedido = new PedidoEntity();
////        new_pedido.setCliente("PruebaHibernate");
////        new_pedido.setFecha(sqlDate);
////        new_pedido.setEstado("PENDIENTE");
////        Integer id = PedidoDAO.insertarPedido(new_pedido);
////        System.out.println("Id insertada: " + id);
////
////        // Creacion del Listado de Productos deseados
////        ArrayList<CartaEntity> lista_productos = new ArrayList<>();
////        CartaEntity p1 = CartaDAO.infoProducto(2);
////        CartaEntity p2 = CartaDAO.infoProducto(3);
////        CartaEntity p3 = CartaDAO.infoProducto(5);
////        lista_productos.add(p1);
////        lista_productos.add(p2);
////        lista_productos.add(p3);
////
////
//////        id = PedidoDAO.insertarPedido(new_pedido);
////        System.out.println("Id del producto actualizado: "+id);
////
////
////
////
//////        // Mostrar todos los pedidos Pendientes
//////        System.out.println("Pedidos Pendientes: ");
//////        ArrayList<PedidoEntity> lista_pedidos = PedidoDAO.listarAllPedidos(true);
//////        lista_pedidos.forEach(k -> {
//////            System.out.println(k.toString());
//////        });
//////        // Mostrar todos los pedidos
//////        System.out.println("All Pedidos: ");
//////        lista_pedidos = PedidoDAO.listarAllPedidos(false);
//////        lista_pedidos.forEach(k -> {
//////            System.out.println(k.toString());
//////        });
//    }
//}