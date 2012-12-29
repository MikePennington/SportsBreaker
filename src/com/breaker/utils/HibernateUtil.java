package com.breaker.utils;

import org.hibernate.*;
import org.hibernate.cfg.*;

public class HibernateUtil
{

    private static final SessionFactory sessionFactory;

    static
    {
        try
        {
            // Create the SessionFactory from hibernate.cfg.xml
            Configuration configuration = new Configuration();
            configuration = configuration.configure();
            sessionFactory = configuration.buildSessionFactory();
        }
        catch (Throwable ex)
        {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }

}