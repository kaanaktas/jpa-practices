package com.sps.util;


import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class DataWrapper {
    private String employeeName;
    private String employeeSalary;
    private String publicationName;
    private String publicationYear;
    private String publicationType;
    private String employeeEmail;
    private String employeePhoneHome;
    private String employeePhoneWork;
    private String employeeCityHome;
    private String employeePostCodeHome;
    private String employeeCityWork;
    private String employeePostCodeWork;
    private String employeeDepartment;
}
