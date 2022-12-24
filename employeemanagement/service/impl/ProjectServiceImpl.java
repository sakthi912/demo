/* 
 * Copyright (c) Sakthi Annamalai, Inc. All Rights Reserved.
 * 
 */
package com.ideas2it.employeemanagement.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.employeemanagement.constants.Constants;
import com.ideas2it.employeemanagement.exceptions.EMSException;
import com.ideas2it.employeemanagement.model.Project;
import com.ideas2it.employeemanagement.dao.ProjectDao;
import com.ideas2it.employeemanagement.dao.impl.ProjectDaoImpl;
import com.ideas2it.employeemanagement.dto.EmployeeDto;
import com.ideas2it.employeemanagement.dto.ProjectDto;
import com.ideas2it.employeemanagement.mapper.Mapper;
import com.ideas2it.employeemanagement.service.ProjectService;

/**
 * ProjectServiceImpl for projects in employee Management System
 *
 * @version 1.8.0_281
 * @author Sakthi Annamalai 
 */
@Service
public class ProjectServiceImpl implements ProjectService {
   
    @Autowired
    private ProjectDao projectDao;
    
    /**
     * Gets employees from database
     *
     * @return project list
     */  
    public List<ProjectDto> getAllProjects() throws EMSException {
        List<ProjectDto> projects = new ArrayList<ProjectDto>();
	
        for(Project project: projectDao.getAllProjects()) {
            projects.add(Mapper.projectEmployeeToDto(project));
        }
        return projects;
    }  

    /**
     * Stores projects record in database
     *
     * @param projectDto
     *
     * @return true if project created else false
     */
    public boolean createProject(ProjectDto projectDto) throws EMSException {
        boolean isCreate = false;
       
        if (1 < (projectDao.createProject(Mapper. toProject
                (projectDto)))) {
            isCreate = true;
        }
        return isCreate;      
    }
        
    /**
     * Checks projects id that already exists in database
     *
     * @param id
     *
     * @return true if project exists else false
     */
    public boolean isProjectExists(int id) throws EMSException {
        boolean isExists = false;

        if(null != projectDao.showSingleProject(id)) {
            isExists = true;
        }
        return isExists; 
    } 

    /**
     * Checks database is empty or not
     * 
     * @return true if database empty else false
     */
    public boolean checkDataBaseEmpty() throws EMSException {
        return getAllProjects().isEmpty();
    }

    /**
     * Shows single project record
     *
     * @param id
     *
     * @return project
     */
    public ProjectDto showSingleProject(int id) throws EMSException {
        return Mapper.projectEmployeeToDto
            (projectDao.showSingleProject(id));
    }

    /**
     * update project fields
     *
     * @param id
     * @param projectDto
     *
     * @return true if project updated else false
     */
    public boolean updateProject(ProjectDto projectDto) throws EMSException {
         boolean isUpdate = false;

         if (0 < (projectDao.updateProject(Mapper.dtoToProjectEmployee
                 (projectDto)))) {
             isUpdate = true;
         }
         return isUpdate; 
    }

    /**
     * Deletes all project records
     *
     * @return isDelete
     */
    public boolean deleteAllProject() throws EMSException {
         boolean isDelete = false;

         if (0 < (projectDao.deleteAllProject())) {
             isDelete = true;
         }
         return isDelete;   
    }      
    
    /**
     * Deletes single project record
     *
     * @param id
     *
     * @return isDelete 
     */
    public boolean deleteSingleProject(int id) throws EMSException {
         boolean isDelete = false;

         if (0 < (projectDao.deleteSingleProject(id))) {
             isDelete = true;
         }
         return isDelete;   
    } 
    
    /**
     * Checks employees already assigned to projects
     *
     * @param projectId, employeeId
     *
     * @return true if employee assigned else false
     */
    public boolean isAlreadyAssigned(int projectId, int employeeId) throws EMSException {
        boolean isPresent = false;
        
        ProjectDto project = showSingleProject(projectId);  
        
        for (EmployeeDto employee : project.getEmployees()) {
            if ((employeeId == employee.getId())) {
                isPresent = true;
                break; 
             }
        } 
        return isPresent;  
    }      
}   
     