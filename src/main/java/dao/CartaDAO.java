package dao;

import entity.CartaEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateUtils;

import java.util.ArrayList;

/**
 * Controlador de los Productos (TABLE: carta)
 */
public class CartaDAO {

    /**
     * Devuelve un producto de la Base de Datos con la 'Id' indicada
     *
     * @param id Identificador (Id) unico del producto. Este es gestionado por la base de Datos
     * @return El Producto asociado a la 'Id' indicada con la informacion guardada en la base de Datos
     */
    public static CartaEntity infoProducto(Integer id) {
        CartaEntity carta = new CartaEntity();

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            carta = session.get(carta.getClass(), id);
        } catch (HibernateException e) {
            System.err.println(e);
        }
        return carta;
    }

    /**
     * Permite guardar un producto ya creado en la base de Datos
     *
     * @param producto Objeto de la clase Producto que se desea guardar en la Base de Datos
     * @return Id del producto insertado (Gestionado por la Base de Datos)
     */
    public static Integer insertarProducto(CartaEntity producto) throws Exception {
        Integer id;

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tst = session.beginTransaction();
            session.persist(producto);
            tst.commit();


        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }

        id = producto.getId();
        System.out.println("Id insertada --> " + id);

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            String sql_query = "select max(producto.id) FROM CartaEntity producto";
            id = (Integer) session.createQuery(sql_query).uniqueResult();
            producto.setId(id);

        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }

        return id;
    }

    /**
     * Permite eliminar un Producto de la BD
     *
     * @param id Identificador unico del Producto que se desea eliminar de la base de Datos
     * @return <ul>
     * <li><i>True</i>: Si se ha podido Eliminar</li>
     * <li><i>False</i>: Si ha habido algun error. Lo que significa que no se actualiza nada</li>
     * </ul>
     */
    public static boolean eliminarProducto(Integer id) throws Exception {
        boolean eliminado = false;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tst = session.beginTransaction();
            String sql_query = "DELETE CartaEntity WHERE id=:id";
            Query query = session.createQuery(sql_query);
            query.setParameter("id", id);
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
     * Lista todos los productos que hay en la BD. Suponemos que se encuentran todos disponibles
     *
     * @return Listado de Productos
     */
    public static ArrayList<CartaEntity> carta() {
        ArrayList<CartaEntity> carta = new ArrayList<>();
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            String sql_query = "SELECT carta from CartaEntity carta";
            var query = session.createQuery(sql_query);
            carta = (ArrayList<CartaEntity>) query.list();

        } catch (HibernateException e) {
            System.err.println(e);
        }
        return carta;
    }
}
