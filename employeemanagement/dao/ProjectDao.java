/* 
 * Copyright (c) Sakthi Annamalai, Inc. All Rights Reserved.
 * 
 */
package com.ideas2it.employeemanagement.dao;

import java.sql.Connection;
import java.util.List;

import com.ideas2it.employeemanagement.exceptions.EMSException;
import com.ideas2it.employeemanagement.model.Project;

/**
 * This class is only interact with Database
 *
 * @version 1.8.0_281
 * @author Sakthi Annamalai
 */
public interface ProjectDao {
    
    /**
     * Creates the projects record in database
     *
     * @param project 
     *
     * @return rowsAffected
     */
    int createProject(Project project) throws EMSException;

    /**
     * Gets all projects record from database
     *
     * @return projects
     */
    List<Project> getAllProjects() throws EMSException;

    /**
     * Shows the single project record in database
     *
     * @param id
     *
     * @return project
     */
    Project showSingleProject(int id) throws EMSException;  

    /**
     * Update all project fields in database
     *
     * @param id
     * @param employee
     *
     * @return rowsAffected
     */
    int updateProject(Project project) throws EMSException;    

    /**
     * Deletes all project in database
     *
     * @return rowsAffected
     */
    int deleteAllProject() throws EMSException;
    
    /**
     * Deletes single project in database
     *
     * @param id
     *
     * @return rowsAffected
     */
    int deleteSingleProject(int id) throws EMSException;         
}