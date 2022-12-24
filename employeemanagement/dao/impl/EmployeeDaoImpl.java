/* 
 * Copyright (c) Sakthi Annamalai, Inc. All Rights Reserved.
 * 
 */
package com.ideas2it.employeemanagement.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.ideas2it.employeemanagement.constants.Constants;
import com.ideas2it.employeemanagement.exceptions.EMSException;
import com.ideas2it.employeemanagement.databaseconnection.HibernateSession;
import com.ideas2it.employeemanagement.dao.EmployeeDao;
import com.ideas2it.employeemanagement.logger.LoggerFactory;
import com.ideas2it.employeemanagement.model.Employee;

/**
 * This class stores,fetch,updates the employees in database
 *
 * @version 1.8.0_281
 * @author Sakthi Annamalai
 */
@Repository
public class EmployeeDaoImpl implements EmployeeDao {             
   
    /**
     * Creates the employees record in database
     *
     * @param employee
     */
    public int createEmployees(Employee employee) throws EMSException {
        int employeeId = 0;
        Transaction transaction = null;
        try {
            Session session = HibernateSession.getFactory().getCurrentSession();
            transaction  = session.beginTransaction();
            employeeId = (Integer) session.save(employee);
            transaction.commit();
        } catch (HibernateException hibernateException) {
            if (null == transaction) {
                transaction.rollback();
            }   
            LoggerFactory.getInstance().error(Constants.EMSEXCEPTION_FOR_CREATE_EMPLOYEE);
            throw new EMSException(Constants.EMSEXCEPTION_FOR_CREATE_EMPLOYEE);       
        }    
        return employeeId; 
    }

    /**
     *
     * Gets employees details from database
     *
     * @return employees
     */
    public List<Employee> getAllEmployees() throws EMSException {
        Transaction transaction = null;
        List<Employee> employees = new ArrayList<Employee>();
        try {
            Session session = HibernateSession.getFactory().getCurrentSession();
            transaction  = session.beginTransaction();
            Query query = session.createQuery("select distinct e from "+
                    "Employee e left join fetch e.projects", Employee.class);
            employees = query.list();
            transaction.commit();
        } catch (HibernateException hibernateException) {
            if (null == transaction) {
                transaction.rollback();
            }
            LoggerFactory.getInstance().error(Constants.EMSEXCEPTION_FOR_CREATE_EMPLOYEE);
            throw new EMSException(Constants.EMSEXCEPTION_FOR_GETALLEMPLOYEES);
        } 
        return employees;
    }   

    /**
     * Shows the single employee records in database
     *
     * @param id
     *
     * @return employee
     */
    public Employee showSingleEmployee(int id) throws EMSException {
        Transaction transaction = null;
        Employee employee = null;

        try {
            Session session = HibernateSession.getFactory().getCurrentSession();
            transaction  = session.beginTransaction();
            Query query = session.createQuery("select distinct e from "
                    + "Employee e left join fetch e.projects where "
                    + "e.id =: employeeId", Employee.class);
            query.setParameter("employeeId", id);
            employee = (Employee) query.uniqueResult();
            transaction.commit();
        } catch (HibernateException hibernateException) {
            if (null == transaction) {
                transaction.rollback();
            }
            throw new EMSException(Constants.EMSEXCEPTION_FOR_SHOWSINGLEEMPLOYEES);
        } 
        return employee;
    }

    /**
     * Deletes all employees in database
     *
     * @return rowsAffected
     */
    public int deleteAllEmployees() throws EMSException {
        int rowsAffected = 0;
        Transaction transaction = null;
        try {
            Session session = HibernateSession.getFactory().getCurrentSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("delete from Employee");
            rowsAffected = query.executeUpdate();
            transaction.commit();
        } catch(HibernateException hibernateException) {
            if (null == transaction) {
                transaction.rollback();
            }
            throw new EMSException(Constants.DELETEALLEMPLOYEES_EMSEXCEPTION);
        }
        return rowsAffected;       
    }
      
    /**
     * Deletes single employee in database
     *
     * @param id
     *
     * @return rowsAffected
     */
    public int deleteSingleEmployee(int id) throws EMSException {
        int rowsAffected = 0;
        Transaction transaction = null;
        Employee employee =null;

        try {
            Session session = HibernateSession.getFactory().getCurrentSession();
            transaction  = session.beginTransaction();
            Query query = session.createQuery("delete from Employee e where "+
                    "e.id = : employeeId");
            query.setParameter("employeeId", id);
            rowsAffected = query.executeUpdate();
            transaction.commit();
        } catch (HibernateException hibernateException) {
            if (null == transaction) {
                transaction.rollback();
            }
            throw new EMSException(Constants.DELETESINGLEEMPLOYEES_EXCEPITONS);
        } 
        return rowsAffected;
    }
     
    /**
     * Updates employee all fields in database
     *
     * @param id
     * @param name
     *
     * @return rowsAffected
     */
    public int updateEmployee(Employee employee) throws EMSException {
        int count = 1;
        Transaction transaction = null;

        try {
            Session session = HibernateSession.getFactory().getCurrentSession();
            transaction  = session.beginTransaction();
            session.saveOrUpdate(employee);
            transaction.commit();
        } catch (HibernateException hibernateException) {
            if (null == transaction) {
                transaction.rollback();
            }
            throw new EMSException(Constants.EMSEXCEPTION_FOR_UPDATEEMPLOYEE);
        } 
        return count; 
    }
    
    /**
     *
     * Gets employees details from database except user
     * given id
     *
     */
    public List<Long> getPhoneNumber(int id) throws EMSException {
        Transaction transaction = null;
        List<Long> contactNumbers = new ArrayList<Long>();
    
        try {
            Session session = HibernateSession.getFactory().getCurrentSession();
            transaction  = session.beginTransaction();
            Query query = session.createQuery("select phoneNumber from Employee "
                    +"e where e.id != : employeeId");
            query.setParameter("employeeId", id);
            contactNumbers = query.list();
            transaction.commit();
        } catch (HibernateException hibernateException) {
            if (null == transaction) {
                transaction.rollback();
            }
            throw new EMSException(Constants.UPDATE_EMAILID_EMSEXCEPTION);
        } 
        return contactNumbers;
    }

    public List<String> getEmail(int id) throws EMSException {
        Transaction transaction = null;
        List<String> employeesEmail = new ArrayList<String>();
    
        try {
            Session session = HibernateSession.getFactory().getCurrentSession();
            transaction  = session.beginTransaction();
            Query query = session.createQuery("select emailId from Employee "
                    +"e where e.id != : employeeId");
            query.setParameter("employeeId", id);
            employeesEmail = query.list();
            transaction.commit();
        } catch (HibernateException hibernateException) {
            if (null == transaction) {
                transaction.rollback();
            }
            throw new EMSException(Constants.UPDATE_EMAILID_EMSEXCEPTION);
        } 
        return employeesEmail;
    }
}
