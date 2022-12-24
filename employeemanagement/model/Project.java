/* 
 * Copyright (c) Sakthi Annamalai, Inc. All Rights Reserved.
 */
package com.ideas2it.employeemanagement.model;

import java.util.HashSet;
import java.util.Set;

import com.ideas2it.employeemanagement.model.Employee;

/**
 * Contains all project fields variables 
 * id,name,domain,descriptions in private
 *
 * @version 1.8.0_281
 * @author Sakthi Annamalai 
 */
public class Project {
    private int id;
    private String domain;
    private String description;
    private String name;
    private Set<Employee> employees = new HashSet<Employee>();

    public Project() {

    }
   
    public Project(String name, String domain, String description) {
        this.name = name;
        this.domain = domain;
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getDomain() {
        return domain;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees; 
    }

    public Set<Employee> getEmployees() {
        return employees; 
    }
}    