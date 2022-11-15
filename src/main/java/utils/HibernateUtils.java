package utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {
    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration config = new Configuration();
            config.configure();

            sessionFactory = config.buildSessionFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
