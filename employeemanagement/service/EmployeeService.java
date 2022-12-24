/* 
 * Copyright (c) Sakthi Annamalai, Inc. All Rights Reserved.
 * 
 */
package com.ideas2it.employeemanagement.service;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

import com.ideas2it.employeemanagement.exceptions.EMSException;
import java.util.Scanner;

import com.ideas2it.employeemanagement.dto.EmployeeDto;

/**
 * This interface is for EmployeeServiceImpl
 *
 * @version 1.8.0_281
 * @author Sakthi Annamalai 
 */
public interface EmployeeService { 
  
   /**
    * Gets employees from database
    *
    * @return employee set
    */  
    List<EmployeeDto> getAllEmployees() throws EMSException;

    /**
     * Stores employees record in database
     *
     * @param employee
     *
     * @return true if it is employee created else false
     */
    boolean createEmployees(EmployeeDto employeeDto) throws EMSException;

    /**
     * Checks employees id that already exists
     *
     * @param id
     *
     * @return true if id existsvalid else false
     */
    boolean isEmployeeExists(int id) throws EMSException;
 
    /**
     * Checks database is empty or not
     * 
     * @return true if it is valid else false
     * @throws EMSException 
     */
    boolean checkDataBaseEmpty() throws EMSException;
    /**
     * Checks employees phoneNumber that already exists
     *
     * @param phoneNumber
     *
     * @return true if phone number exists else false
     */
    boolean isPhoneNumberExists(long phoneNumber) throws EMSException; 
   
    /**
     * Checks emailid that already exists
     *
     * @param emailid
     *
     * @return true if email Id exists else false
     */
    public boolean isEmailExists(String emailId) throws EMSException;

    /**
     * Shows single employees record
     *
     * @param id
     */
    EmployeeDto showSingleEmployee(int id) throws EMSException;

    /**
     * Deletes all employees records
     *
     * @return isDelete
     */
    boolean deleteAllEmployees() throws EMSException;
    
    /**
     * Deletes single employee record
     *
     * @param id
     *
     * @return isDelete
     */
    boolean deleteSingleEmployee(int id) throws EMSException;
 
    /**
     * updates employee
     *
     * @param employeeDto
     */
    boolean updateEmployee(EmployeeDto employee) throws EMSException;
 
    /**
     * Checks phone number already exists or not 
     * excepts user given id
     *
     * @param phoneNumber
     * @param id
     *
     * @return true if phone Number exist else false
     */
    boolean isDuplicatePhoneNumber(long phoneNumber, int id) throws EMSException;

    /**
     * Checks emailId already exists or not 
     * excepts user given id
     *
     * @param emailId
     * @param id
     *
     * @return true if email id exists else false 
     */
    boolean isDuplicateEmail(String emailId, int id) throws EMSException;
  
    /**
     * Checks employeeId, projectId already exists or not 
     * 
     * @param employeeId
     * @param projectId
     *
     * @return true or false
     */
    public boolean isAlreadyAssigned(int employeeId, int projectId) throws EMSException;
}
    

    



 
        

       