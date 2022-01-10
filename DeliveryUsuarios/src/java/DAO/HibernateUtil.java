/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author dream
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory;
    
    static {
        try {
            //CREADO NUEVO HIBERNATEUTIL (EL ANTERIOR ESTABA DEPRECADO)
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            // config file.
            Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
            StandardServiceRegistryBuilder srb = new StandardServiceRegistryBuilder();
            srb.applySettings(cfg.getProperties());
            StandardServiceRegistry sr = srb.build();
            sessionFactory = cfg.buildSessionFactory(sr);
 
        } catch (Throwable ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
