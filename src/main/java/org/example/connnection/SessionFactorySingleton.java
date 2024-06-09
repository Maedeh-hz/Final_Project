package org.example.connnection;

import org.example.model.*;
import org.example.model.custom_fields.Address;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.awt.*;

public class SessionFactorySingleton {
    private SessionFactorySingleton() {}

    private static class LazyHolder {
        static SessionFactory INSTANCE;

        static {
            var registry = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml")
                    .build();

            INSTANCE = new MetadataSources(registry)
                    .addAnnotatedClass(Admin.class)
                    .addAnnotatedClass(Customer.class)
                    .addAnnotatedClass(Expert.class)
                    .addAnnotatedClass(Order.class)
                    .addAnnotatedClass(Service.class)
                    .addAnnotatedClass(Subservice.class)
                    .addAnnotatedClass(Suggestion.class)
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(Wallet.class)
                    .addAnnotatedClass(User_SubService.class)
                    .addAnnotatedClass(Address.class)
                    .buildMetadata()
                    .buildSessionFactory();
        }
    }

    public static SessionFactory getInstance() {
        return LazyHolder.INSTANCE;
    }

}
