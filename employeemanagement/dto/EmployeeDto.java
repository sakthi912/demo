/* 
 * Copyright (c) Sakthi Annamalai, Inc. All Rights Reserved.
 * 
 */
package com.ideas2it.employeemanagement.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.ideas2it.employeemanagement.dto.ProjectDto;

/**
 * Employee - contains variables 
 * (age,salary,name,phone number,id,emailId,dateOfbirth).
 *
 * @version 1.8.0_281
 * @author Sakthi Annamalai 
 */
public class EmployeeDto {
    private int id;
    private int age;
    private double salary;
    private long phoneNumber;
    private String name;
    private String emailId;
    private String dateOfBirth;
    private Set<ProjectDto> projects;

    public EmployeeDto() {

    }
   
    public EmployeeDto (String name, String dateOfBirth,
        long phoneNumber, double salary, String emailId) {
        
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.salary = salary;
        this.emailId = emailId;
        this.projects = projects;
    }
    
    public void setName(String name) {
        this.name = name;
    } 
    
    public String getName() {
        return name;
    }
 
    public void setId(int id) {
        this.id = id;
    }  
    
    public int getId() {
        return id;
    }  
 
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }  
    
    public String getDateOfBirth() {
        return dateOfBirth;
    }  
    
    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }
  
    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }
 
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
    
    public String getEmailId() {
        return emailId;
    }
   
    public void setProjects(Set<ProjectDto> projects) {
        this.projects = projects; 
    }

    public Set<ProjectDto> getProjects() {
        return projects; 
    }
    
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n Name                        : ")
                     .append(getName())        
                     .append("\n ID                          : ")
                     .append(getId())
                     .append("\n Date of Birth (YYYY-MM-DD)  : ")
                     .append(getDateOfBirth())
                     .append("\n PhoneNumber                 : ")
                     .append(getPhoneNumber())
                     .append("\n Salary                      : ")
                     .append(getSalary())
                     .append("\n Email Id                    : ")
                     .append(getEmailId());
        return stringBuilder.toString();
    }
}
       