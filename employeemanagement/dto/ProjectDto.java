/* 
 * Copyright (c) Sakthi Annamalai, Inc. All Rights Reserved.
 * 
 */
package com.ideas2it.employeemanagement.dto;

import java.util.HashSet;
import java.util.Set;

import com.ideas2it.employeemanagement.dto.EmployeeDto;

/**
 * Contains all project fields variables 
 * id,name,domain,descriptions in private
 *
 * @version 1.8.0_281
 * @author Sakthi Annamalai 
 */
public class ProjectDto {
    private int id;
    private String domain;
    private String description;
    private String name;
    private Set<EmployeeDto> employees = new HashSet<EmployeeDto>();

    public ProjectDto() {

    }

    public ProjectDto(String name,String domain,String description) {
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

    public void setEmployees(Set<EmployeeDto> employees) {
        this.employees = employees; 
    }

    public Set<EmployeeDto> getEmployees() {
        return employees; 
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n Id          : ")
                     .append(getId())
                     .append("\n Name        : ")
                     .append(getName())
                     .append("\n Domain      : ")
                     .append(getDomain())
                     .append("\n Description : ")
                     .append(getDescription());
        return stringBuilder.toString();
    }
}    