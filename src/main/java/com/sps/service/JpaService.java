package com.sps.service;

import com.sps.entity.*;
import com.sps.entity.PublicationType;
import com.sps.util.DataParser;
import com.sps.util.DataWrapper;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class JpaService {
    protected EntityManager em;

    public JpaService(EntityManager em){
        this.em = em;
    }

    public void processBulkData() throws Exception {
        List<DataWrapper> result = DataParser.CsvParser();

        em.getTransaction().begin();
        result.stream()
                .forEach(p -> {
                    Department isDepartmentExist = checkDepartment(p.getEmployeeDepartment());
                    if(isDepartmentExist == null){
                        Department dp = new Department();
                        dp.setName(p.getEmployeeDepartment());
                        Employee employee = new Employee();
                        employee.setName(p.getEmployeeName());
                        employee.setAddress(
                                new HashMap<String, Address>(){
                                    {
                                        put("home", new Address(p.getEmployeeCityHome(), p.getEmployeePostCodeHome()));
                                        put("work", new Address(p.getEmployeeCityWork(), p.getEmployeePostCodeWork()));
                                    }
                                }
                        );
                        employee.setEmailAddresses(p.getEmployeeEmail());
                        employee.setPhoneNumbers(
                                new HashMap<String, String>(){
                                    {
                                        put("home", p.getEmployeePhoneHome());
                                        put("work", p.getEmployeePhoneWork());
                                    }
                                }
                        );
                        employee.setSalary(Long.valueOf(p.getEmployeeSalary()));
                        employee.setPublication(new Publication(p.getPublicationName(), p.getPublicationYear(), PublicationType.valueOf(p.getPublicationType())));

                        dp.setEmployees(Arrays.asList(employee));
                        em.persist(dp);
                    }

                });

        em.getTransaction().commit();
    }

    private Department checkDepartment(String name) {
        //Typed Query - not in use
        TypedQuery<Department> query = em.createQuery("SELECT d from Department d WHERE d.name = :name", Department.class)
                .setParameter("name", name);

        //Named Query
        TypedQuery<Department> namedQuery = em.createNamedQuery("Department.findByName", Department.class)
                .setParameter("name", name);

        Department result = query.getResultList().stream().findFirst().orElse(null);
        return result;
    }

    public void findAllDepartmentsWithEmployees(){
        List<Department> departments = (List<Department>) em.createQuery("Select d From Department d").getResultList();

        departments.forEach( e -> {
            System.out.println("Department Name:"+e.getName());

            e.getEmployees().forEach(k -> {
                System.out.println("Employee Name:"+k.getName()+". City:"+k.getAddress().get("home").getCity()
                +".Publication: "+k.getPublication().getName());
            });
        });
    }

    public void removeDepartment(){
        em.getTransaction().begin();
        Department dp = em.find(Department.class, 1);
        em.remove(dp);
        em.getTransaction().commit();
    }

    public void removeEmployee(){
        em.getTransaction().begin();
        Department dp = em.find(Department.class, 1);
        Employee employee = dp.getEmployees().get(0);
        dp.removeEmployee(employee);
        em.remove(employee);
        em.getTransaction().commit();
    }
}
