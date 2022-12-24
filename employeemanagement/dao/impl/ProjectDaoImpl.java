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
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.ideas2it.employeemanagement.constants.Constants;
import com.ideas2it.employeemanagement.exceptions.EMSException;
import com.ideas2it.employeemanagement.databaseconnection.HibernateSession;
import com.ideas2it.employeemanagement.dao.ProjectDao;
import com.ideas2it.employeemanagement.model.Project;

/**
 * This class stores,fetch,updates the projects in database
 *
 * @version 1.8.0_281
 * @author Sakthi Annamalai
 */
@Repository
public class ProjectDaoImpl implements ProjectDao {
    
    /**
     * Creates the projects record in database
     *
     * @param project 
     *
     * @return rowsAffected 
     */
    public int createProject(Project project) throws EMSException {
        int projectId = 0;
        Transaction transaction = null;

        try {
            Session session = HibernateSession.getFactory().getCurrentSession();
            transaction  = session.beginTransaction();
            projectId =  (Integer) session.save(project);
            transaction.commit();
        } catch (HibernateException hibernateException) {
        	hibernateException.printStackTrace();
        	if (null == transaction) {
                transaction.rollback();
            }   
            throw new EMSException(Constants.EMSEXCEPTION_FOR_CREATE_PROJECT);
        } 
        return projectId; 
    }
    
    /**
     * Gets projects details from database
     *
     * @return projects
     */
    public List<Project> getAllProjects() throws EMSException {
        Transaction transaction = null;
        List<Project> projects = new ArrayList<Project>();

        try {
            Session session = HibernateSession.getFactory().getCurrentSession();
            transaction  = session.beginTransaction();
            Query query = session.createQuery("select distinct p from "+
                    "Project p left join fetch p.employees",Project.class);
            projects = query.list();
            transaction.commit();
        } catch (HibernateException hibernateException) {
            throw new EMSException(Constants.EMSEXCEPTION_FOR_GETALLPROJECTS);
        } 
        return projects;
    }   

    /**
     * Shows the single project records in database
     *
     * @param id
     *
     * @return project
     */
    public Project showSingleProject(int id) throws EMSException {
        Transaction transaction = null;
        Project project = null;

        try {
            Session session = HibernateSession.getFactory().getCurrentSession();
            transaction  = session.beginTransaction();
            Query query = session.createQuery("select distinct p from "+ 
                    "Project p left join fetch p.employees where p.id =: id",Project.class);
            query.setParameter("id", id);
            project = (Project) query.uniqueResult();
            transaction.commit();
        } catch (HibernateException hibernateException) {
            throw new EMSException(Constants.EMSEXCEPTION_FOR_SHOWSINGLEPROJECTS);
        } 
        return project;
    }

    /**
     * Deletes all project in database
     *
     * @return rowsAffected
     */
    public int deleteAllProject() throws EMSException {
        int rowsAffected = 0;
        Transaction transaction = null;

        try {
            Session session = HibernateSession.getFactory().getCurrentSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("delete from Project");
            rowsAffected = query.executeUpdate();
            transaction.commit();
        } catch(HibernateException hibernateException) {
            if (null == transaction) {
                transaction.rollback();
            }
            throw new EMSException(Constants.DELETEALLPROJECTS_EMSEXCEPTION);
        }
        return rowsAffected;
    }
      
    /**
     * Deletes single project in database
     *
     * @param id
     *
     * @return rowsAffected
     */
    public int deleteSingleProject(int id) throws EMSException {
        int rowsAffected = 0;
        Transaction transaction = null;
        Project project =null;

        try {
            Session session = HibernateSession.getFactory().getCurrentSession();
            transaction  = session.beginTransaction();
            Query query = session.createQuery("delete from Project p where p.id = : projectId");
            query.setParameter("projectId", id);
            rowsAffected = query.executeUpdate();
            transaction.commit();
        } catch (HibernateException hibernateException) {
            throw new EMSException(Constants.DELETESINGLPROJETS_EXCEPITONS);
        } 
        return rowsAffected;
    }   

    /**
     * Updates project in database
     *
     * @param id
     * @parm project
     *
     * @return rowsAffected
     */
    public int updateProject(Project project) throws EMSException {
        Transaction transaction = null;

        try {
            Session session = HibernateSession.getFactory().getCurrentSession();
            transaction  = session.beginTransaction();
            session.saveOrUpdate(project);
            transaction.commit();
        } catch (HibernateException hibernateException) {
            throw new EMSException(Constants.EMSEXCEPTION_FOR_UPDATEPROJECTS);
        } 
        return 1; 
    }                  
} 