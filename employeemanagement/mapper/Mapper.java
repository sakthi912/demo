/*
 * Copyright (c) 2022 Ideas2it, Inc. All Rights Reserved.
 *
 */
package com.ideas2it.employeemanagement.mapper;

import java.util.HashSet;
import java.util.Set;

import com.ideas2it.employeemanagement.dto.EmployeeDto;
import com.ideas2it.employeemanagement.dto.EmployeeDto;
import com.ideas2it.employeemanagement.dto.ProjectDto;
import com.ideas2it.employeemanagement.model.Employee;
import com.ideas2it.employeemanagement.model.Project;

/**
 * Class converts Employee object into EmployeeDTO object and
 * converts EmployeeDTO object into Employee object.
 *
 * @author Sakthi Annamalai
 *
 * @version 4.0
 */
public class Mapper {

    public Mapper() {
    }

    /**
     * Converts EmployeeDto object into Employee object.
     *
     * @param employeeDto
     *
     * @return employee 
     */
    public static Employee toEmployee(EmployeeDto employeeDto) {
        Employee employee = new Employee();

        employee.setId(employeeDto.getId());
        employee.setPhoneNumber(employeeDto
                .getPhoneNumber());
        employee.setSalary(employeeDto.getSalary());
        employee.setName(employeeDto.getName());
        employee.setEmailId(employeeDto.getEmailId());
        employee.setDateOfBirth(employeeDto.getDateOfBirth());
        return employee;
    }

    /**
     * Converts EmployeeDTO object into Employee object.
     *
     * @param employee
     *
     * @return employeeDto
     */
    public static EmployeeDto toEmployeeDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
				
        employeeDto.setId(employee.getId());
        employeeDto.setPhoneNumber(employee
                .getPhoneNumber());
        employeeDto.setSalary(employee.getSalary());
        employeeDto.setName(employee.getName());
        employeeDto.setEmailId(employee.getEmailId());
        employeeDto.setDateOfBirth(employee.getDateOfBirth());
        return employeeDto;
    }

    /**
     * Converts ProjectDTO object into Project object.
     *
     * @param projectDto 
     *
     * @return project
     */
    public static Project toProject(ProjectDto projectDto) {
        Project project = new Project();

        project.setId(projectDto.getId());
        project.setName(projectDto.getName());
        project.setDomain(projectDto.getDomain());
        project.setDescription(projectDto.getDescription());
        return project;
    }

    /**
     * Converts Project object into ProjectDto object.
     *
     * @param project 
     *
     * @return projectDto 
     */
    public static ProjectDto toProjectDto(Project project) {
        ProjectDto projectDto = new ProjectDto();

        projectDto.setId(project.getId());
        projectDto.setName(project.getName());
        projectDto.setDomain(project.getDomain());
        projectDto.setDescription(project.getDescription());
        return projectDto;
    }

    /**
     * Converts Employee object into EmployeeDto object and Project
     * object into ProjectDTO object.
     *
     * @param employee 
     *
     * @return employeeDto
     */
    public static EmployeeDto employeeProjectToDto(Employee employee) {
        EmployeeDto employeeDto = toEmployeeDto(employee);
        Set<ProjectDto> projectDtoList = new HashSet<ProjectDto>();

        if (! (employee.getProjects().isEmpty())) {
            for (Project project : employee.getProjects()) {
                projectDtoList.add(toProjectDto(project));
            }
        }
        employeeDto.setProjects(projectDtoList);
        return employeeDto;
    }

    /**
     * Converts EmployeeDTO object into Employee object and
     * ProjectDTO object inot Project object.
     *
     * @param employeeDto
     *
     * @return employee 
     */
    public static Employee dtoToEmployeeProject(EmployeeDto employeeDto) {
        Employee employee = toEmployee(employeeDto);
        Set<Project> projectList = new HashSet<Project>();

        if (! (employeeDto.getProjects().isEmpty())) {
            for (ProjectDto projectDto : employeeDto.getProjects()) {
                projectList.add(toProject(projectDto));
            }
        }
        employee.setProjects(projectList);
        return employee;
    }

    /**
     * Converts Project object into ProjectDto object and Employee
     * object into EmployeeDto object
     *
     * @param project as Project 
     *
     * @return projectDto 
     */
    public static ProjectDto projectEmployeeToDto(Project project) {
        Set<EmployeeDto> employeeDtoList = new HashSet<EmployeeDto>();
        ProjectDto projectDto = toProjectDto(project);

        if (! (project.getEmployees().isEmpty())) {
            for (Employee employee : project.getEmployees()) {
                employeeDtoList.add(Mapper.toEmployeeDto(employee));
            }
        }  
        projectDto.setEmployees(employeeDtoList);
        return projectDto;
    }

    /**
     * Converts ProjectDTO object into Project object and EmployeeDTO
     * as Employee object.
     *
     * @param projectDto 
     *
     * @return project
     */
    public static Project dtoToProjectEmployee(ProjectDto projectDto) {
        Project project = toProject(projectDto);
        Set<Employee> employeeList = new HashSet<Employee>();

        if (! (projectDto.getEmployees().isEmpty())) {
            for (EmployeeDto employeeDto : projectDto.getEmployees()) {
                employeeList.add(toEmployee(employeeDto));
            }
        }  
        project.setEmployees(employeeList);
        return project;
    }
}