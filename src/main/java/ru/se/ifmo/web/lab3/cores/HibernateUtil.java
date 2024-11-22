package ru.se.ifmo.web.lab3.cores;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import ru.se.ifmo.web.lab3.data.Results;

public class HibernateUtil {
    private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();
                settings.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
                settings.put(Environment.DRIVER, "org.postgresql.Driver");
                settings.put(Environment.URL, "jdbc:postgresql://localhost:5432/studs?useSSL=false");
                settings.put(Environment.USER, "s408693");
                settings.put(Environment.PASS, "Bz0CyvPlvRB5J307");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.HBM2DDL_AUTO, "update");

                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(Results.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}