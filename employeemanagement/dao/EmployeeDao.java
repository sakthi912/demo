/* 
 * Copyright (c) Sakthi Annamalai, Inc. All Rights Reserved.
 * 
 */
package com.ideas2it.employeemanagement.dao;

import java.time.LocalDate;
import java.util.List;

import com.ideas2it.employeemanagement.exceptions.EMSException;
import com.ideas2it.employeemanagement.model.Employee;
import com.ideas2it.employeemanagement.model.Project;
import com.ideas2it.employeemanagement.dto.ProjectDto;

/**
 * This interface is for EmployeeDaoImpl
 *
 * @version 1.8.0_281
 * @author Sakthi Annamalai
 */
public interface EmployeeDao {
 
    /**
     * Creates the employees record in database
     *
     * @param employee
     *
     * @return rowsAffected
     */
    int createEmployees(Employee employee) throws EMSException; 
 
    /**
     * Shows the single employee records in database
     *
     * @param id
     *
     * @return rowsAffected
     */
    Employee showSingleEmployee(int id) throws EMSException;  

    /**
     * Deletes all employees in database
     *
     * @return rowsAffected
     */
    int deleteAllEmployees() throws EMSException;
    
    /**
     * Deletes single employee in database
     *
     * @param id
     *
     * @return rowsAffected
     */
    int deleteSingleEmployee(int id) throws EMSException;
   
    /**
     * Updates employee all fields in database
     *
     * @param id
     * @param name
     *
     * @return rowsAffected
     */
    int updateEmployee(Employee employee) throws EMSException;   

    /**
     *
     * Gets employees details from database
     *
     * @return employees
     */
    List<Employee> getAllEmployees() throws EMSException;   

    /**
     *
     * Gets employees phone number details from database
     *
     * @param id
     * @return employees
     */
    List<Long> getPhoneNumber(int id) throws EMSException;

    /**
     *
     * Gets employees emailId details from database
     *
     * @param id
     *
     * @return employees
     */
    List<String> getEmail(int id) throws EMSException;
}     
