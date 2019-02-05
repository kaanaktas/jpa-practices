package com.sps.service;

import com.sps.config.JPAUtil;
import javax.persistence.EntityManager;

public class MainApp {
    public static void main(String[] args) throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();

        JpaService service = new JpaService(entityManager);
        service.processBulkData();
        service.findAllDepartmentsWithEmployees();
        entityManager.close();
        JPAUtil.shutdown();
    }
}
