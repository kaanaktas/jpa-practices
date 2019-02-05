package com.sps.config;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
    private static final String PERSISTENCE_UNIT_NAME = "JpaService";
    private static EntityManagerFactory factory;
    private static final Logger hibernateSqlLog = Logger.getLogger("org.hibernate.SQL");

    public static EntityManagerFactory getEntityManagerFactory() {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
        return factory;
    }

    public static void shutdown() {
        if (factory != null) {
            factory.close();
        }
    }

    public static void enableHibernateSqlTrace(){
        hibernateSqlLog.setLevel(Level.ALL);
    }

    public static void disableHibernateSqlTrace(){
        hibernateSqlLog.setLevel(Level.OFF);
    }
}
