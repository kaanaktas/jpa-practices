package com.sps.service;

import com.sps.entity.*;
import com.sps.entity.PublicationType;
import com.sps.util.DataParser;
import com.sps.util.DataWrapper;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class JpaService {
    protected EntityManager em;

    public JpaService(EntityManager em) {
        this.em = em;
    }

    public void processBulkData() throws Exception {
        List<DataWrapper> result = DataParser.CsvParser();

        em.getTransaction().begin();
        result.stream()
                .forEach(p -> {
                    Department department = checkDepartment(p.getEmployeeDepartment());

                    if (department == null) {
                        department = new Department();
                        department.setName(p.getEmployeeDepartment());
                    }

                    Employee employee = new Employee();
                    employee.setName(p.getEmployeeName());
                    employee.setAddress(
                            new HashMap<String, Address>() {
                                {
                                    put("home", new Address(p.getEmployeeCityHome(), p.getEmployeePostCodeHome()));
                                    put("work", new Address(p.getEmployeeCityWork(), p.getEmployeePostCodeWork()));
                                }
                            }
                    );
                    employee.setEmailAddresses(p.getEmployeeEmail());
                    employee.setPhoneNumbers(
                            new HashMap<String, String>() {
                                {
                                    put("home", p.getEmployeePhoneHome());
                                    put("work", p.getEmployeePhoneWork());
                                }
                            }
                    );
                    employee.setSalary(Long.valueOf(p.getEmployeeSalary()));
                    employee.setPublication(new Publication(p.getPublicationName(), p.getPublicationYear(), PublicationType.valueOf(p.getPublicationType())));

                    department.addEmployee(employee);
                    em.persist(department);
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

        Department result = namedQuery.getResultList().stream().findFirst().orElse(null);
        return result;
    }

    public void findAllDepartmentsWithEmployees() {
        List<Department> departments = (List<Department>) em.createQuery("Select d From Department d").getResultList();

        if (departments == null || departments.isEmpty()) {
            throw new RuntimeException("No department found!");
        }

        departments.forEach(e -> {
            System.out.println("Department Name:" + e.getName());

            e.getEmployees().forEach(k -> {
                System.out.println("Employee Name:" + k.getName() + ". City:" + k.getAddress().get("home").getCity()
                        + ".Publication: " + k.getPublication().getName());
            });
        });
    }

    public void removeDepartmentById(int departmentId) {
        em.getTransaction().begin();
        Department dp = em.find(Department.class, departmentId);
        em.remove(dp);
        em.getTransaction().commit();
    }

    public void removeEmployeeByDepartmentAndById(int departmentId, int employeeId) {
        em.getTransaction().begin();
        Department dp = em.find(Department.class, departmentId);
        Employee employee = dp.getEmployees().get(employeeId);
        dp.removeEmployee(employee);
        em.remove(employee);
        em.getTransaction().commit();
    }

    public Employee findEmployeeByCriteriaByName(String name){
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Employee> employeeCriteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> employeeRoot = employeeCriteriaQuery.from(Employee.class);

        Predicate predicateEmployeeName = criteriaBuilder.equal(employeeRoot.get("name"), name);
        Predicate predicate = criteriaBuilder.and(predicateEmployeeName,
                criteriaBuilder.like(employeeRoot.get("department").get("name"), "%DEPARTMENT_%"));

        employeeCriteriaQuery.where(predicate);
        Employee employee = em.createQuery(employeeCriteriaQuery).getResultList().stream().findAny().orElse(null);

        return employee;
    }

    public void nativeQueries(){

        List<Object[]> query1 = em.createQuery(
                "SELECT SUM(e.salary), AVG(e.salary), MAX(e.salary), MIN(e.salary) from Employee e", Object[].class).getResultList();
        query1.forEach(d ->
                System.out.println(Arrays.asList(d).toString()));

        List<Object[]> query2 = em.createQuery(
                "SELECT e.department.name, AVG(e.salary*1.0) from Employee e GROUP BY e.department.name", Object[].class).getResultList();
        query2.forEach(d ->
                System.out.println(Arrays.asList(d).toString()));
    }
}
