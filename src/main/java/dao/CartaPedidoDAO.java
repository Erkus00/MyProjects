package dao;

import entity.CartaEntity;
import entity.CartaPedidoEntity;
import entity.PedidoEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtils;

import java.util.ArrayList;
import java.util.List;

public class CartaPedidoDAO {

    public static void enlace(ArrayList<CartaPedidoEntity> selecciones) {
        for (Integer i=0; i<selecciones.size(); i++) {
            try (Session session = HibernateUtils.getSessionFactory().openSession()) {
                Transaction tst = session.beginTransaction();
                session.persist(selecciones.get(i));
                tst.commit();
                System.out.println("\n\t"+i);
            } catch (HibernateException e) {
                throw new HibernateException(e);
            }
        }
    }
}
