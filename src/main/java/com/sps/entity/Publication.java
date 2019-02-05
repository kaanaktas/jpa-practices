package com.sps.entity;

import javax.persistence.*;

@Entity
public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String date;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publication_id")
    private Employee employee;
    @Enumerated(EnumType.STRING)
    private PublicationType publicationType;

    public Publication() {
    }

    public Publication(String name, String date, PublicationType publicationType) {
        this.name = name;
        this.date = date;
        this.publicationType = publicationType;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public PublicationType getPublicationType() {
        return publicationType;
    }

    public void setPublicationType(PublicationType publicationType) {
        this.publicationType = publicationType;
    }
}
