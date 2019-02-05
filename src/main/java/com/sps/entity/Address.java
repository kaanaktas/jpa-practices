package com.sps.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Address {

    private String city;
    private String postCode;

    public Address(){

    }

    public Address(String city, String postCode) {
        this.setCity(city);
        this.setPostCode(postCode);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String zipcode) {
        this.postCode = zipcode;
    }
}
