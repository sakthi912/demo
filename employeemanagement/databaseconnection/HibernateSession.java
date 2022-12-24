/* 
 * Copyright (c) Sakthi Annamalai, Inc. All Rights Reserved.
 * 
 */
package com.ideas2it.employeemanagement.databaseconnection;

import org.hibernate.cfg.Configuration;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Create session object.
 */
public class HibernateSession {

    private static SessionFactory sessionFactory = null;

    /**
     * No arg constructor to initialize the sessionFactory object is a type of
     * SessionFactory when the object is created.
     */
    private HibernateSession() {
    }

    /**
     * Initialize the sessionFactory and create the session object.
     *
     * @return Session.     
     */
    public static SessionFactory getFactory() {
        try { 
            if (null == sessionFactory) {
                Configuration configuration = new Configuration();
                configuration = configuration.configure("resource/hibernate.cfg.xml");
                sessionFactory = configuration.buildSessionFactory();
            }
        } catch(HibernateException hibernateException) {
            hibernateException.printStackTrace();
        }
        return sessionFactory;
    }
}