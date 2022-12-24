/* 
 * Copyright (c) Sakthi Annamalai, Inc. All Rights Reserved.
 * 
 */
package com.ideas2it.employeemanagement.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.ideas2it.employeemanagement.model.Project;

/**
 * Contains Employee fields age,salary,name,phone number,
 * id,emailId,dateOfBirth in private.
 *
 * @version 1.8.0_281
 * @author Sakthi Annamalai 
 */
public class Employee {
    private int id;
    private int age;
    private double salary;
    private long phoneNumber;
    private String name;
    private String emailId;
    private String dateOfBirth;
    private Set<Project> projects;
      
    public Employee() {

    }

    public Employee (String name, String dateOfBirth,
        long phoneNumber, double salary, String emailId) {
        
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.salary = salary;
        this.emailId = emailId;
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

    public void setProjects(Set<Project> projects) {
        this.projects = projects; 
    }
       
    public Set<Project> getProjects() {
        return projects; 
    }
}
       