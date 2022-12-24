/* 
 * Copyright (c) Sakthi Annamalai, Inc. All Rights Reserved.
 * 
 */
package com.ideas2it.employeemanagement.service;

import java.util.List;

import com.ideas2it.employeemanagement.exceptions.EMSException;
import com.ideas2it.employeemanagement.dto.ProjectDto;

/**
 * This interface is for projectServiceImpl
 *
 * @version 1.8.0_281
 * @author Sakthi Annamalai
 */
public interface ProjectService {
 
    /**
     * Stores projects record in database
     *
     * @param projectDto
     *
     * @return true if project created else false
     */
    boolean createProject(ProjectDto projectDto) throws EMSException;
 
   /**
    * Gets employees from database
    *
    * @return project list
    */  
    List<ProjectDto> getAllProjects() throws EMSException;

    /**
     * Checks projects id that already exists
     *
     * @param id
     *
     * @return true if project id exists else false
     */
    boolean isProjectExists(int id) throws EMSException;

    /**
     * Shows single project records
     *
     * @param id
     */
    ProjectDto showSingleProject(int id) throws EMSException;

    /**
     * update project
     *
     * @param project
     *
     * @return  true if project updated else false
     */
    boolean updateProject(ProjectDto projectDto) throws EMSException;

    /**
     * Checks database is empty or not
     * 
     * @return true if database empty else false
     */
    boolean checkDataBaseEmpty() throws EMSException;
    /**
     * Deletes all project records
     *
     * @return true if project deleted else false
     */
    boolean deleteAllProject() throws EMSException;
    
    /**
     * Deletes single project record
     *
     * @param id
     *
     * @return true if project deleted else false
     */
    boolean deleteSingleProject(int id) throws EMSException;

    /**
     * Checks employeeId, projectId already exists or not 
     * 
     * @param employeeId
     * @param projectId
     *
     * @return true or false
     */
    boolean isAlreadyAssigned(int projectId, int employeeId) throws EMSException;     
}   
     