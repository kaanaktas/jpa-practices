package com.sps.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
public class Employee {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(unique = true, columnDefinition = "BINARY(16)")
    private UUID id;

    private String name;
    private long salary;

    @ElementCollection(targetClass = Address.class)
    @CollectionTable(name = "EMPLOYEE_ADDRESS")
    @AttributeOverrides(value = {
            @AttributeOverride(name = "postCode", column = @Column(name = "zipCode")),
            @AttributeOverride(name = "city", column = @Column(name = "province"))
        }
    )
    private Map<String, Address> address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToOne(
            mappedBy = "employee",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            optional = false
    )
    private Publication publication;

    private String emailAddresses;

    @ElementCollection
    @CollectionTable(name = "EMPLOYEE_PHONE")
    @MapKeyColumn(name = "PHONE_TYPE")
    @Column(name = "PHONE_NUMBER")
    private Map<String,String> phoneNumbers;

    public Employee(){
    }

    public Employee(String name, long salary, Publication publication, String emailAddresses, Map<String, String> phoneNumbers, Map<String, Address> address){
        setName(name);
        setSalary(salary);
        setAddress(address);
        setPublication(publication);
        setEmailAddresses(emailAddresses);
        setPhoneNumbers(phoneNumbers);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }


    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
        if (publication != null){
            publication.setEmployee(this);
        }
    }

    public String getEmailAddresses() {
        return emailAddresses;
    }

    public void setEmailAddresses(String emailAddresses) {
        this.emailAddresses = emailAddresses;
    }

    public Map<String, String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(Map<String, String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public Map<String, Address> getAddress() {
        return address;
    }

    public void setAddress(Map<String, Address> address) {
        this.address = address;
    }
}
