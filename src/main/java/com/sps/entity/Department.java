package com.sps.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name="Department.findByName", query = "SELECT d FROM Department d Where d.name = :name")
        })
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @OneToMany(mappedBy = "department",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private List<Employee> employees;

    public Department(){
        setEmployees(new ArrayList<Employee>());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public void addEmployee(Employee employee){
        getEmployees().add(employee);
        employee.setDepartment(this);
    }

    public void removeEmployee(Employee employee){
        getEmployees().remove(employee);
        employee.setDepartment(null);
    }
}
