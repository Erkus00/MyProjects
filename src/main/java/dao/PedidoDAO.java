package dao;

import entity.CartaEntity;
import entity.CartaPedidoEntity;
import entity.PedidoEntity;
import model.Pedido;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateUtils;

import java.util.ArrayList;

import static view.View.clean;

/**
 * Controlador de los Pedidos (TABLE: pedido)
 */
public class PedidoDAO {

    /**
     * Muestra toda la informacion de un pedido sin importar su estado ('PENDIENTE' o 'RECOGIDO')
     *
     * @param id Identificador del pedido. Un mismo pedido comparte identificador
     * @return El Pedido guardado en la Base de Datos con el <i>Identificador</i> Indicado
     */
    public static Pedido infoPedido(Integer id) {
        PedidoEntity pedido_local = new PedidoEntity();

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            pedido_local = session.get(pedido_local.getClass(), id);
        } catch (HibernateException e) {
            System.err.println(e);
        }
        ArrayList<CartaPedidoEntity> pedido_entities = new ArrayList<>();
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {

            String hql_query = "from CartaPedidoEntity where idPedido=:id";
            var query = session.createQuery(hql_query, CartaPedidoEntity.class);
            query.setParameter("id", id);
            pedido_entities = (ArrayList<CartaPedidoEntity>) query.list();

        } catch (HibernateException e) {
            System.err.println(e);
        }
        return new Pedido(pedido_local, pedido_entities);
    }

    /**
     * Permite crear un nuevo Pedido, siempre que el producto exista
     *
     * @param pedido_local Objeto de tipo 'Pedido' que se desea insertar en la BD
     * @return Id del pedido insertado. (Gestionado por la base de Datos)
     */
    public static Integer insertarPedido(Pedido pedido_local) {
        PedidoEntity pedido = pedido_local.getInfo_pedido();
        ArrayList<CartaPedidoEntity> lista_productos = pedido_local.getInfo_productos();
        Integer id = 0;

        if (pedido != null && !lista_productos.isEmpty()) {
            try (Session session = HibernateUtils.getSessionFactory().openSession()) {
                Transaction tst = session.beginTransaction();
                session.persist(pedido);
                tst.commit();
            } catch (HibernateException e) {
                throw new RuntimeException(e);
            }

            try (Session session = HibernateUtils.getSessionFactory().openSession()) {
                String sql_query = "select max(pedido.id) FROM PedidoEntity pedido";
                id = session.createQuery(sql_query, Integer.class).uniqueResult();
                pedido.setId(id);

            } catch (HibernateException e) {
                throw new RuntimeException(e);
            }
            ArrayList<CartaPedidoEntity> asociar_productos = new ArrayList<>();
            Integer finalId = id;
            lista_productos.forEach((k) -> {
                CartaPedidoEntity cpe = new CartaPedidoEntity();
                cpe.setIdProducto(k.getIdProducto());
                cpe.setCantidad(k.getCantidad());
                cpe.setIdPedido(finalId);
                asociar_productos.add(cpe);
            });
            enlace(asociar_productos);
        }

        return id;
    }

    /**
     * Permite insertar los productos elegidos en un pedido en la Tabla intermedia 'carta_pedido'
     *
     * @param selecciones
     */
    public static void enlace(ArrayList<CartaPedidoEntity> selecciones) {
        for (Integer i = 0; i < selecciones.size(); i++) {
            try (Session session = HibernateUtils.getSessionFactory().openSession()) {
                Transaction tst = session.beginTransaction();
                session.persist(selecciones.get(i));
                tst.commit();
                System.out.println("\n\t" + i);
            } catch (HibernateException e) {
                throw new HibernateException(e);
            }
        }
    }

    /**
     * Permite eliminar un pedido siempre que este exista (Se encuentre hubicado en la base de datos)
     *
     * @param id Identificador unico del pedido. Un mismo pedido comparte identificador
     * @return <ul>
     * <li><i>True</i>: Si se ha podido Eliminar</li>
     * <li><i>False</i>: Si ha habido un error a la hora de eliminar el pedido.Lo que significa que no se actualiza nada</li>
     * </ul>
     */
    public static boolean eliminarPedido(Integer id) throws Exception {
        boolean eliminado = false;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tst = session.beginTransaction();
            String sql_query = "DELETE CartaPedidoEntity WHERE idPedido=:i";
            Query query = session.createQuery(sql_query, null);
            query.setParameter("i", id);
            query.executeUpdate();
            tst.commit();

        } catch (HibernateException e) {
            throw new Exception(e);
        }

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tst = session.beginTransaction();
            String sql_query = "DELETE PedidoEntity WHERE id=:i";
            Query query = session.createQuery(sql_query, null);
            query.setParameter("i", id);
            int i = query.executeUpdate();

            if (i != 0) {
                eliminado = true;
            }
            tst.commit();

        } catch (HibernateException e) {
            throw new Exception(e);
        }
        return eliminado;
    }

    /**
     * Marca un pedido como recogido, actualizando la BD
     *
     * @param id Identificador del pedido que se desea recoger. Un mismo pedido comparte identificador
     * @return <ul>
     * <li><i>True</i>: Si se ha podido Recoger</li>
     * <li><i>False</i>: Si ha habido un error a la hora de recoger el pedido. Lo que significa que no se actualiza nada</li>
     * </ul>
     */

    public static boolean recogerPedido(Integer id) throws Exception {
        boolean updated = false;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tst = session.beginTransaction();
            String sql_query = "UPDATE PedidoEntity SET estado='RECOGIDO' WHERE id=:i";
            Query query = session.createQuery(sql_query, null);
            query.setParameter("i", id);
            int i = query.executeUpdate();

            if (i != 0) {
                updated = true;
            }
            tst.commit();

        } catch (HibernateException e) {
            throw new Exception(e);
        }
        return updated;
    }

    /**
     * Muestra todas las comandas realizadas desde el comienzo del programa
     * Si se desea, puede hacerse consulta de aquellos pedidos que sean exclusivamente pendientes,
     * en caso contrario, la consulta devolverá todos los pedidos existentes
     *
     * @param pendiente <ul> <li><i>True</i>: Listar solo los pedidos pendientes</li>
     *                  <li><i>False</i>: Listar todos los pedidos</li>
     *                  </ul>
     * @return Listado con los pedidos que cumplen la condicion deseada
     */
    public static ArrayList<Pedido> listarAllPedidos(boolean pendiente) {
        ArrayList<Pedido> pedidos = new ArrayList<>();
        ArrayList<Integer> lista_id_pedidos = new ArrayList<>();
        String hql_query = "";
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            if (!pendiente) {
                hql_query = "SELECT id from PedidoEntity pedido";

            } else {
                hql_query = "SELECT id from PedidoEntity pedido where estado='PENDIENTE'";
            }
            var query = session.createQuery(hql_query, Integer.class);
            lista_id_pedidos = (ArrayList<Integer>) query.list();

        } catch (HibernateException e) {
            System.err.println(e);
        }
        lista_id_pedidos.forEach(k -> {
            Pedido pedido_temp = infoPedido(k);
            pedidos.add(pedido_temp);
        });
        return pedidos;
    }

    /**
     * Muestra todas las comandas de una fecha en concreto
     *
     * @param fecha_inicio Fecha desde la que se desean obtener las comandas
     * @param fecha_fin    Fecha final de la que de desea hacer la consulta
     * @param pendiente    <ul> <li><i>True</i>: Listar solo los pedidos pendientes</li>
     *                     <li><i>False</i>: Listar todos los pedidos</li>
     *                     </ul>
     * @return ArrayList de los pedidos comprendidos desde la fecha de inicio hasta la fecha de fin; ambas inclusive
     */
    public static ArrayList<Pedido> listarPedidosFecha(java.sql.Date fecha_inicio, java.sql.Date fecha_fin, boolean pendiente) {
        ArrayList<Pedido> pedidos = new ArrayList<>();
        ArrayList<Integer> lista_id_pedidos_fecha = new ArrayList<>();
        String sql_query = "";
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            if (pendiente) {
                sql_query = "SELECT id from PedidoEntity where fecha>=:f1 AND fecha<=:f2 AND estado='PENDIENTE'";
            } else {
                sql_query = "SELECT id from PedidoEntity where fecha>=:f1 AND fecha<=:f2";
            }
            var query = session.createQuery(sql_query, Integer.class);
            query.setParameter("f1", fecha_inicio);
            query.setParameter("f2", fecha_fin);
            lista_id_pedidos_fecha = (ArrayList<Integer>) query.list();

        } catch (HibernateException e) {
            System.err.println(e);
        }

        lista_id_pedidos_fecha.forEach(k -> {
            Pedido pedido_temp = infoPedido(k);
            pedidos.add(pedido_temp);
        });
        return pedidos;
    }

    /**
     * Lista los pedidos asociados a un cliente siempre que estos hayan sido entregados
     *
     * @param nombre_cliente Nombre del cliente del que se desean obtener los pedidos
     * @return ArrayList de los pedidos asociados a un cliente
     */
    public static ArrayList<Pedido> pedidosCliente(String nombre_cliente) {
        ArrayList<Pedido> pedidos = new ArrayList<>();
        ArrayList<Integer> lista_id_pedidos_cliente = new ArrayList<>();
        String sql_query = "";
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {

            sql_query = "SELECT id from PedidoEntity pedido where cliente=:c";
            var query = session.createQuery(sql_query, Integer.class);
            query.setParameter("c", nombre_cliente);
            lista_id_pedidos_cliente = (ArrayList<Integer>) query.list();

        } catch (HibernateException e) {
            System.err.println(e);
        }
        lista_id_pedidos_cliente.forEach(k -> {
            Pedido pedido_temp = infoPedido(k);
            pedidos.add(pedido_temp);
        });
        return pedidos;
    }

    /**
     * Consulta a la BD los pedidos que se encuentran pendientes
     */

    public static void pedidosPendientes() {
        ArrayList<Pedido> pedidos_pendientes = listarAllPedidos(true);

        System.out.println("Lista de los pedidos actuales que se encuentran pendientes");
        clean(2);
        pedidos_pendientes.forEach(k -> {
            System.out.println(k.infoView());
            clean(1);
        });
    }
}


