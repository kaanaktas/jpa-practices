package com.sps.service;

import com.sps.config.JPAUtil;
import com.sps.entity.Employee;
import org.junit.*;

import javax.persistence.EntityManager;

/**
 * Created by kaktas on 05-Feb-19
 */

public class JpaServiceTest {

    private static EntityManager entityManager = null;
    private static JpaService jpaService = null;

    @BeforeClass
    public static void setUpClass() throws Exception {
        JPAUtil.disableHibernateSqlTrace();
        entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        jpaService = new JpaService(entityManager);
        jpaService.processBulkData();
    }

    @Test
    public void TestFindAllDepartmentsWithEmployees(){
        JPAUtil.enableHibernateSqlTrace();
        jpaService.findAllDepartmentsWithEmployees();
        JPAUtil.disableHibernateSqlTrace();
    }

    @Test
    public void TestRemoveDepartment(){
        JPAUtil.enableHibernateSqlTrace();
        jpaService.removeDepartmentById(1);
        JPAUtil.disableHibernateSqlTrace();
    }

    @Test
    public void TestRemoveEmployee(){
        JPAUtil.enableHibernateSqlTrace();
        jpaService.removeEmployeeByDepartmentAndById(2, 2);
        JPAUtil.disableHibernateSqlTrace();
     }

     @Test
     public void TestFindEmployeeByCriteriaByName(){
         JPAUtil.enableHibernateSqlTrace();
         Employee employee = jpaService.findEmployeeByCriteriaByName("Timothee Stuckey");
         System.out.println("Employee Full Name:"+employee.getName()+" Employee Department:"+employee.getDepartment().getName());
         JPAUtil.disableHibernateSqlTrace();
     }

    @Test
    public void TestNativeQueries(){
        JPAUtil.enableHibernateSqlTrace();
        jpaService.nativeQueries();
        JPAUtil.disableHibernateSqlTrace();
    }

    @Test
    public void TestTestTriggerRollBack(){
        JPAUtil.enableHibernateSqlTrace();
        jpaService.triggerRollBack();
        JPAUtil.disableHibernateSqlTrace();
    }


    @AfterClass
    public static void tearDownClass(){
        entityManager.close();
        JPAUtil.shutdown();
    }
}
