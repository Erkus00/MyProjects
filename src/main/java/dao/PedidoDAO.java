package dao;

import entity.PedidoEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateUtils;

import java.util.ArrayList;

/**
 * Controlador de los Pedidos (TABLE: pedido)
 */
public class PedidoDAO {

    /**
     * Muestra toda la informacion de un pedido sin importar su estado ('PENDIENTE' o 'RECOGIDO')
     *
     * @param identificador Identificador del pedido. Un mismo pedido comparte identificador
     * @return El Pedido guardado en la Base de Datos con el <i>Identificador</i> Indicado
     */
    static PedidoEntity infoPedido(Integer identificador) {
        PedidoEntity carta = new PedidoEntity();

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            carta = session.get(carta.getClass(), identificador);
        } catch (HibernateException e) {
            System.err.println(e);
        }
        return carta;
    }

    /**
     * Permite crear un nuevo Pedido, siempre que el producto exista
     *
     * @param pedido Objeto de tipo 'PedidoEntity' que se desea insertar en la BD
     * @return Id del pedido insertado. (Gestionado por la base de Datos)
     */
    static Integer insertarPedido(PedidoEntity pedido) {
        Integer id = null;

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tst = session.beginTransaction();
            session.persist(pedido);
            tst.commit();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            String sql_query = "select max(pedido.id) FROM PedidoEntity pedido";
            id = (Integer) session.createQuery(sql_query).uniqueResult();
            pedido.setId(id);

        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
        return id;
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
    static boolean eliminarPedido(Integer id) throws Exception {
        boolean eliminado = false;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tst = session.beginTransaction();
            String sql_query = "DELETE FROM PedidoEntity WHERE id=:i";
            Query query = session.createQuery(sql_query);
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

    static boolean recogerPedido(Integer id) throws Exception {
        boolean updated = false;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tst = session.beginTransaction();
            String sql_query = "UPDATE FROM PedidoEntity SET estado='RECOGIDO' WHERE id=:i";
            Query query = session.createQuery(sql_query);
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
    public static ArrayList<PedidoEntity> listarAllPedidos(boolean pendiente) {
        ArrayList<PedidoEntity> lista_pedidos = new ArrayList<>();
        String sql_query = "";
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            if (!pendiente) {
                sql_query = "SELECT pedido from PedidoEntity pedido";

            } else {
                sql_query = "SELECT pedido from PedidoEntity pedido where estado='PENDIENTE'";
            }
            var query = session.createQuery(sql_query);
            lista_pedidos = (ArrayList<PedidoEntity>) query.list();

        } catch (HibernateException e) {
            System.err.println(e);
        }
        return lista_pedidos;
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
    static ArrayList<PedidoEntity> listarPedidosFecha(java.sql.Date fecha_inicio, java.sql.Date fecha_fin, boolean pendiente) {
        ArrayList<PedidoEntity> lista_pedidos_fecha = new ArrayList<>();
        String sql_query = "";
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            if (pendiente) {
                sql_query = "SELECT pedido from PedidoEntity pedido where fecha>=:f1 AND fecha<=:f2 AND estado='PENDIENTE'";
            } else {
                sql_query = "SELECT pedido from PedidoEntity pedido where fecha>=:f1 AND fecha<=:f2";
            }
            var query = session.createQuery(sql_query);
            query.setParameter("f1", fecha_inicio);
            query.setParameter("f2", fecha_fin);
            lista_pedidos_fecha = (ArrayList<PedidoEntity>) query.list();

        } catch (HibernateException e) {
            System.err.println(e);
        }
        return lista_pedidos_fecha;
    }

    /**
     * Lista los pedidos asociados a un cliente siempre que estos hayan sido entregados
     *
     * @param nombre_cliente Nombre del cliente del que se desean obtener los pedidos
     * @return ArrayList de los pedidos asociados a un cliente
     */
    static ArrayList<PedidoEntity> pedidosCliente(String nombre_cliente) {
        ArrayList<PedidoEntity> lista_pedidos_cliente = new ArrayList<>();
        String sql_query = "";
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {

            sql_query = "SELECT pedido from PedidoEntity pedido where cliente=:c";
            var query = session.createQuery(sql_query);
            query.setParameter("c", nombre_cliente);
            lista_pedidos_cliente = (ArrayList<PedidoEntity>) query.list();

        } catch (HibernateException e) {
            System.err.println(e);
        }
        return lista_pedidos_cliente;
    }

}


