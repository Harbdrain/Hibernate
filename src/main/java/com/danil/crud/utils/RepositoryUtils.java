package com.danil.crud.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class RepositoryUtils {
    private static SessionFactory sessionFactory;

    private static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .loadProperties("hibernate.properties")
                    .build();
            try {
                sessionFactory = new MetadataSources(registry)
                .addAnnotatedClass(com.danil.crud.model.Label.class)
                .addAnnotatedClass(com.danil.crud.model.Post.class)
                .addAnnotatedClass(com.danil.crud.model.Writer.class)
                .buildMetadata().buildSessionFactory();
            } catch (Exception e) {
                StandardServiceRegistryBuilder.destroy(registry);
                System.exit(1);
            }
        }

        return sessionFactory;
    }

    public static Session getSession() {
        return getSessionFactory().openSession();
    }
}
