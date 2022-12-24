/* 
 * Copyright (c) Sakthi Annamalai, Inc. All Rights Reserved.
 * 
 */
package com.ideas2it.employeemanagement.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.employeemanagement.constants.Constants;
import com.ideas2it.employeemanagement.model.Employee;
import com.ideas2it.employeemanagement.dao.EmployeeDao;
import com.ideas2it.employeemanagement.exceptions.EMSException;
import com.ideas2it.employeemanagement.dao.impl.EmployeeDaoImpl;
import com.ideas2it.employeemanagement.dto.EmployeeDto;
import com.ideas2it.employeemanagement.dto.ProjectDto;
import com.ideas2it.employeemanagement.mapper.Mapper;
import com.ideas2it.employeemanagement.service.EmployeeService;

/**
 * EmployeeServiceImpl for employees in employee Management System
 *
 * @version 1.8.0_281
 * @author Sakthi Annamalai 
 */
@Service
public class EmployeeServiceImpl implements EmployeeService { 
    
    @Autowired
    public EmployeeDao employeeDao;
  
   /**
    * Gets employees from database
    *
    * @return employee list
    */  
    public List<EmployeeDto> getAllEmployees() throws EMSException {
       List<EmployeeDto> employees = new ArrayList<EmployeeDto>();

       for(Employee employee: employeeDao.getAllEmployees()) {
           employees.add(Mapper.employeeProjectToDto(employee));
       }
       return employees;
    } 
    
    /**
     * Checks database is empty or not
     * 
     * @return true if it is valid else false
     * @throws EMSException 
     */
    public boolean checkDataBaseEmpty() throws EMSException {
        return getAllEmployees().isEmpty();
    }
            
    /**
     * Stores employees record in database
     *
     * @param employee
     *
     * @return true if it is employee created else false
     */
    public boolean createEmployees(EmployeeDto employeeDto) throws EMSException {
         boolean isCreate = false;

         if (1 < (employeeDao.createEmployees
                 (Mapper.toEmployee(employeeDto)))) {
             isCreate = true;
         }
         return isCreate;
    }   

    /**
     * Checks employees id that already exists in database
     *
     * @param id
     *
     * @return true if employee Exist else false
     */
    public boolean isEmployeeExists(int id) throws EMSException {
        boolean isExists = false;

        if(null != employeeDao.showSingleEmployee(id)) {
            isExists = true;
        }
        return isExists; 
    } 
 
    /**
     * Checks employees phoneNumber that already exists in database
     *
     * @param phoneNumber
     *
     * @return true if it is valid else false
     */
    public boolean isPhoneNumberExists(long phoneNumber) throws EMSException {
        boolean isValid = false;
        
        for (EmployeeDto entry: getAllEmployees()) {
            if (entry.getPhoneNumber() ==  phoneNumber) {
                isValid= true;
                break;
            }
        } 
        return isValid;       
    }
   
    /**
     * Checks employees email Id that already exists in database
     *
     * @param emailid
     *
     * @return true if it is valid else false
     */
    public boolean isEmailExists(String emailId) throws EMSException {
        boolean  isValid = false;
        
        for (EmployeeDto entry: getAllEmployees()) {
            if (entry.getEmailId().equals(emailId)) {
                isValid= true;
                break;
            }
        }
        return isValid;
    }

    /**
     * Shows single employees record
     *
     * @param id
     *
     * @return 
     */
    public EmployeeDto showSingleEmployee(int id) throws EMSException {
        return Mapper.employeeProjectToDto
            (employeeDao.showSingleEmployee(id));
    }

    /**
     * Deletes all employees records
     *
     * @return isDelete
     */
    public boolean deleteAllEmployees() throws EMSException {
         boolean isDelete = false;

         if (0 < (employeeDao.deleteAllEmployees())) {
             isDelete = true;
         }
         return isDelete;   
    }   
    
    /**
     * Deletes single employee record
     *
     * @param id
     *
     * @return isDelete
     */
    public boolean deleteSingleEmployee(int id) throws EMSException {
         boolean isDelete = false;

         if (0 < (employeeDao.deleteSingleEmployee(id))) {
             isDelete = true;
         }
         return isDelete;   
    }     
 
    /**
     * update all employee fields
     *
     * @param id
     * @param employeeDto
     *
     * @return true if employee updated else false
     */
    public boolean updateEmployee(EmployeeDto employeeDto) throws EMSException {
         boolean isUpdate = false;

         if (0 < (employeeDao.updateEmployee(Mapper.dtoToEmployeeProject
                 (employeeDto)))) {
             isUpdate = true;
         }
         return isUpdate; 
    }

    /**
     * Checks phone number already exists or not 
     * excepts user given id
     *
     * @param phoneNumber
     * @param id
     *
     * @return true if phone Number exist else false
     */
    public boolean isDuplicatePhoneNumber(long phoneNumber,int id) throws EMSException {
        boolean isExists = false;
      
        for (Long entry :
                   employeeDao.getPhoneNumber(id)) {
            if (entry == phoneNumber) {
                isExists = true;
                break;
            }
        }
        return isExists;
    }

    /**
     * Checks emailId already exists or not 
     * excepts user given id
     *
     * @param emailId
     * @param id
     *
     * @return true if email id exists else false
     */
    public boolean isDuplicateEmail(String emailId,int id) throws EMSException {
        boolean isExists = false;
      
        for (String entry :
                 employeeDao.getEmail(id)) {
            if (entry.equals(emailId)) {
                isExists = true;
                break;
            }
        }
        return isExists;
    }
 
    /**
     * Checks employeeId, projectId already exists or not 
     * 
     * @param employeeId
     * @param projectId
     *
     * @return true or false
     */
    public boolean isAlreadyAssigned(int employeeId, int projectId) throws EMSException {
        boolean isPresent = false;
    
        EmployeeDto employee = showSingleEmployee(employeeId);  
        
            for (ProjectDto project : employee.getProjects()) {
                if ((projectId == project.getId())) {
                    isPresent = true;
                    break; 
                }
            }
        return isPresent; 
    }           
}       