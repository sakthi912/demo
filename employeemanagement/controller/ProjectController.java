/* 
 * Copyright (c) Sakthi Annamalai, Inc. All Rights Reserved.
 * 
 */
package com.ideas2it.employeemanagement.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServlet;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ideas2it.employeemanagement.exceptions.EMSException;
import com.ideas2it.employeemanagement.constants.Constants;
import com.ideas2it.employeemanagement.dto.EmployeeDto;
import com.ideas2it.employeemanagement.dto.ProjectDto;
import com.ideas2it.employeemanagement.logger.LoggerFactory;
import com.ideas2it.employeemanagement.service.EmployeeService;
import com.ideas2it.employeemanagement.service.ProjectService;
import com.ideas2it.employeemanagement.service.impl.EmployeeServiceImpl;

/**
 * ProjectController for projects in employee Management System
 *
 * @version 1.8.0_281
 * @author Sakthi Annamalai
 */
@Controller
public class ProjectController extends HttpServlet {

    private static final Logger logger = LoggerFactory.getInstance();

    @Autowired
    private ProjectService projectService;

    @Autowired
    private EmployeeService employeeService;

    /**
     * Creates object for ProjectDto and send it to the ProjectForm jsp
     * 
     * @param model The Model instance contains the request made by the client.
     * @return response as a jsp page
     */
    @GetMapping("/registerProject")
    private String registerProject(Model model) {
        ProjectDto projectDto = new ProjectDto();
        model.addAttribute("projectDto", projectDto);
        model.addAttribute("title", "CREATE PROJECT");
        model.addAttribute("formAction", "createProject");
        model.addAttribute("submit", "Create");

        return "ProjectForm.jsp";
    }

    /**
     * Gets the project details from the user in jsp page and sets it the
     * projectDto object
     * 
     * @param projectDto
     * @param redirectAttributes the RedirectAttribute instance for adding
     *                           messages.
     * @return response as a jsp page
     */
    @PostMapping("/createProject")
    private String insertProject(
            @ModelAttribute("projectDto") ProjectDto projectDto,
            RedirectAttributes redirectAttribute) {
        String response = "redirect:/Error.jsp";
        try {
            projectService.createProject(projectDto);
            redirectAttribute.addAttribute("message",
                    Constants.PROJECT_CREATED);
            redirectAttribute.addAttribute("link", "ProjectMainMenu.jsp");
            response = "redirect:/Message.jsp";
        } catch (EMSException emsException) {
            logger.error("Error in Creating Employee");
            redirectAttribute.addAttribute("error",
                    Constants.EMSEXCEPTION_FOR_CREATE_PROJECT);
        }
        return response;
    }

    /**
     * Gets the list of projects from database and forward the response to
     * ViewAllProject jsp page
     * 
     * @param model The Model instance contains the request made by the client.
     * @return response as a jsp page
     */
    @GetMapping("/viewAllProject")
    public String showAllProject(Model model,
            RedirectAttributes redirectAttribute) {
        String response = "redirect:/Error.jsp";

        try {
            if (projectService.checkDataBaseEmpty()) {
                redirectAttribute.addAttribute("message", "Database is Empty");
                redirectAttribute.addAttribute("link", "ProjectMainMenu.jsp");
                response = "redirect:/Message.jsp";
            } else {
                List<ProjectDto> projects = projectService.getAllProjects();
                model.addAttribute("projects", projects);
                response = "ViewAllProject.jsp";
            }
        } catch (EMSException emsException) {
            logger.error(Constants.ERROR_IN_SHOWALL);
            redirectAttribute.addAttribute("message", Constants.EMAILID_EXISTS);
            redirectAttribute.addAttribute("link", "ViewProjectMenu.jsp");
        }
        return response;
    }

    /**
     * Gets the particular project with corresponding employee and forwards the
     * obtained employee to DisplayProjectEmployee jsp page
     * 
     * @param id
     * @param model              The Model instance contains the request made by
     *                           the client.
     * @param redirectAttributes the RedirectAttribute instance for adding
     *                           messages.
     * @return response as a jsp page
     */
    @GetMapping("/displayProjectEmployee")
    private String projectEmployee(@RequestParam("id") int id, Model model,
            RedirectAttributes redirectAttribute) {
        String response = "redirect:/Error.jsp";

        try {
            if (projectService.checkDataBaseEmpty()) {
                redirectAttribute.addAttribute("message", "Database is Empty");
                redirectAttribute.addAttribute("link", "ViewProjectMenu.jsp");
                response = "redirect:/Message.jsp";
            } else {
                ProjectDto project = projectService.showSingleProject(id);
                System.out.println(project.getEmployees());
                model.addAttribute("employees", project.getEmployees());
                response = "DisplayProjectEmployee.jsp";
            }
        } catch (EMSException emsException) {
            logger.error("Error in display Project Employee");
            redirectAttribute.addAttribute("error",
                    "Error in showing Employee Project");
            redirectAttribute.addAttribute("link", "ViewProjectMenu.jsp");
        }
        return response;
    }

    /**
     * Gets the particular project with corresponding project id and forwards
     * the obtained project object to ViewAllProject jsp page
     * 
     * @param id
     * @param model              The Model instance contains the request made by
     *                           the client.
     * @param redirectAttributes the RedirectAttribute instance for adding
     *                           messages.
     * @param redirectAttribute
     * @return response as a jsp page
     */
    @GetMapping("/viewSingleProject")
    private String showSingleProject(@RequestParam("id") int id, Model model,
            RedirectAttributes redirectAttribute) {
        String response = "redirect:/Error.jsp";

        try {
            if (projectService.isProjectExists(id)) {
                List<ProjectDto> projects = new ArrayList<ProjectDto>();
                ProjectDto employee = projectService.showSingleProject(id);
                projects.add(employee);
                model.addAttribute("projects", projects);
                response = "ViewAllProject.jsp";
            } else {
                model.addAttribute("message", Constants.ID_NOT_EXISTS);

                response = "ViewSingleProject.jsp";
            }
        } catch (EMSException emsException) {
            logger.error(Constants.ERROR_IN_SHOWSINGLE);
            redirectAttribute.addAttribute("error", "Error in showing Project");
            redirectAttribute.addAttribute("link", "ViewProjectMenu.jsp");
        }
        return response;
    }

    /**
     * Deletes all project in the database
     * 
     * @param redirectAttribute
     * @return response as a jsp page
     */
    @GetMapping("/deleteAllProject")
    private String deleteAllProject(RedirectAttributes redirectAttribute) {
        String response = "redirect:/Error.jsp";
        try {
            if (projectService.deleteAllProject()) {
                redirectAttribute.addAttribute("message",
                        "Deleted all projects");
                redirectAttribute.addAttribute("link", "ProjectMainMenu.jsp");
                response = "redirect:/Message.jsp";
            } else {
                redirectAttribute.addAttribute("message", "Database is Empty");
                redirectAttribute.addAttribute("link", "ProjectMainMenu.jsp");
                response = "redirect:/Message.jsp";
            }
        } catch (EMSException emsException) {
            logger.error("Error in delete all project");
            redirectAttribute.addAttribute("error",
                    Constants.ERROR_IN_DELETEALL);
        }
        return response;
    }

    /**
     * Deletes the particular Project
     * 
     * @param id
     * @param redirectAttributes the RedirectAttribute instance for adding
     *                           messages.
     * @return response as a jsp page
     */
    @GetMapping("/deleteSingleProject")
    private String deleteSingleProject(@RequestParam("id") int id,
            RedirectAttributes redirectAttribute) {
        String response = "redirect:/Error.jsp";
        try {
            projectService.deleteSingleProject(id);
            redirectAttribute.addAttribute("message",
                    "Project Deleted Successfully");
            redirectAttribute.addAttribute("link", "ViewProjectMenu.jsp");
            response = "redirect:/Message.jsp";
        } catch (EMSException emsException) {
            logger.error(Constants.ERROR_IN_DELETESINGLE);
            redirectAttribute.addAttribute("message",
                    Constants.ERROR_IN_DELETESINGLE);
            redirectAttribute.addAttribute("link", "ViewProjectMenu.jsp");
        }
        return response;
    }

    /**
     * Gets id from user and gets the particular project object and forwards the
     * obtained project object to ProjectForm jsp page
     * 
     * @param id
     * @param model The Model instance contains the request made by the client.
     * @return response as a jsp page
     */
    @GetMapping("/updateProjectById")
    private String updateProjectById(@RequestParam("id") int id, Model model,
            RedirectAttributes redirectAttribute) {
        String response = "redirect:/Error.jsp";
        ProjectDto projectDto;
        try {
            projectDto = projectService.showSingleProject(id);
            model.addAttribute("projectDto", projectDto);
            model.addAttribute("title", "UPDATE PROJECT");
            model.addAttribute("formAction", "updateProject");
            model.addAttribute("submit", "Update");
            response = "ProjectForm.jsp";
        } catch (EMSException emsException) {
            logger.error("Error in getting project id");
            redirectAttribute.addAttribute("message",
                    "Error in Updating Project");
            redirectAttribute.addAttribute("link", "ViewProjectMenu.jsp");
        }
        return response;
    }

    /**
     * Updates the employee details in the employees list to the particular
     * Employee object
     * 
     * @param employeeDto
     * @param redirectAttributes the RedirectAttribute instance for adding
     *                           messages.
     * @return response as a jsp page
     */
    @PostMapping("/updateProject")
    private String updateProject(
            @ModelAttribute("projectDto") ProjectDto projectDto,
            RedirectAttributes redirectAttribute) {
        String response = "redirect:/Error.jsp";
        try {
            int id = projectDto.getId();
            ProjectDto project = projectService.showSingleProject(id);
            projectDto.setEmployees(project.getEmployees());
            projectService.updateProject(projectDto);
            redirectAttribute.addAttribute("message",
                    "Project updated successfully");
            redirectAttribute.addAttribute("link", "ViewProjectMenu.jsp");
            response = "redirect:Message.jsp";
        } catch (EMSException exception) {
            logger.error("Error in Updating Project");
            redirectAttribute.addAttribute("error",
                    "Error in Updating Project");
            redirectAttribute.addAttribute("link", "ViewProjectMenu.jsp");
        }
        return response;
    }

    /**
     * Checks database empty or not and if not forward to AssignOrUnassign jsp
     * page
     * 
     * @param model              The Model instance contains the request made by
     *                           the client.
     * @param redirectAttributes the RedirectAttribute instance for adding
     *                           messages.
     * @return response as a jsp page
     */
    @GetMapping("/assignEmployeeForm")
    private String getAssignForm(Model model,
            RedirectAttributes redirectAttribute) {
        String response = "rediect:/Error.jsp";
        try {
            if (employeeService.checkDataBaseEmpty()) {
                redirectAttribute.addAttribute("message", "Database is empty");
                redirectAttribute.addAttribute("link",
                        "AssignOrUnassignMenu.jsp");
                response = "redirect:Message.jsp";
            } else {
                model.addAttribute("title", "ASSIGN EMPLOYEE");
                model.addAttribute("formAction", "assignEmployee");
                response = "AssignOrUnassign.jsp";
            }
        } catch (EMSException emsException) {
            logger.error("Error in getting unassign employee form");
            redirectAttribute.addAttribute("error", "error in  assign form");
        }
        return response;
    }

    /**
     * Gets employeeId and projectId from user and assign employees to project
     * 
     * @param employeeId
     * @param projectId
     * @param model              The Model instance contains the request made by
     *                           the client.
     * @param redirectAttributes the RedirectAttribute instance for adding
     *                           messages.
     * @return response as a jsp page
     */
    @PostMapping("/assignEmployee")
    private String assignEmployee(@RequestParam("employeeId") int employeeId,
            @RequestParam("projectId") int projectId, Model model,
            RedirectAttributes redirectAttribute) {

        String response = "redirect:/Error.jsp";
        ProjectDto project;
        EmployeeDto employeeDto;
        Set<EmployeeDto> employees;

        try {
            if (projectService.isProjectExists(projectId)) {
                if (employeeService.isEmployeeExists(employeeId)) {

                    if (projectService.isAlreadyAssigned(projectId,
                            employeeId)) {
                        redirectAttribute.addAttribute("message",
                                Constants.ALREADY_ASSIGN);
                        redirectAttribute.addAttribute("link",
                                "AssignOrUnassignProject.jsp");
                        response = "redirect:/Message.jsp";
                    } else {
                        project = projectService.showSingleProject(projectId);
                        employeeDto = employeeService
                                .showSingleEmployee(employeeId);
                        employees = project.getEmployees();
                        employees.add(employeeDto);
                        project.setEmployees(employees);
                        projectService.updateProject(project);
                        redirectAttribute.addAttribute("message",
                                "Employee assigned to project");
                        redirectAttribute.addAttribute("link",
                                "AssignOrUnassignProject.jsp");
                        response = "redirect:/Message.jsp";
                    }
                } else {
                    redirectAttribute.addAttribute("message",
                            Constants.EMPLOYEE_NOT_EXISTS);
                    redirectAttribute.addAttribute("link",
                            "AssignOrUnassignProject.jsp");
                    redirectAttribute.addAttribute("formAction",
                            "assignEmployee");
                    return "redirect:/Message.jsp";
                }
            } else {
                logger.warn(Constants.PROJECT_NOT_EXISTS);
                redirectAttribute.addAttribute("message",
                        Constants.PROJECT_NOT_EXISTS);
                redirectAttribute.addAttribute("link",
                        "AssignOrUnassignProject.jsp");
                response = "redirect:/Message.jsp";
            }
        } catch (EMSException emsException) {
            logger.error("Error in Assign Employee");
            redirectAttribute.addAttribute("error", "Error in Assign Employee");
            redirectAttribute.addAttribute("link",
                    "AssignOrUnassignProject.jsp");
        }
        return response;
    }

    /**
     * Checks database empty or not and if not forward to AssignOrUnassign jsp
     * page
     * 
     * @param model
     * @param redirectAttribute
     * @return response as a jsp page
     */
    @GetMapping("/unAssignEmployeeForm")
    private String getUnAssignForm(Model model,
            RedirectAttributes redirectAttribute) {
        String response = "rediect:/Error.jsp";
        try {
            if (employeeService.checkDataBaseEmpty()) {
                redirectAttribute.addAttribute("message", "Database is empty");
                redirectAttribute.addAttribute("link",
                        "AssignOrUnassignMenu.jsp");
                response = "redirect:Message.jsp";
            } else {
                model.addAttribute("title", "UNASSIGN EMPLOYEE");
                model.addAttribute("formAction", "unAssignEmployee");
                response = "AssignOrUnassign.jsp";
            }
        } catch (EMSException emsException) {
            logger.error("Error in getting unassign employee form");
            redirectAttribute.addAttribute("error", "error in  unassign form");
        }
        return response;
    }

    /**
     * Gets employeeId and projectId from user and unassign employees to project
     * 
     * @param employeeId
     * @param projectId
     * @param model              The Model instance contains the request made by
     *                           the client.
     * @param redirectAttributes the RedirectAttribute instance for adding
     *                           messages.
     * @return response as a jsp page
     */
    @PostMapping("/unAssignEmployee")
    private String unAssignEmployee(@RequestParam("projectId") int projectId,
            @RequestParam("employeeId") int employeeId, Model model,
            RedirectAttributes redirectAttribute) {
        String response = "redirect:/Error.jsp";
        ProjectDto project;
        EmployeeDto employeeDto;
        Set<EmployeeDto> employees;

        try {

            if (projectService.isProjectExists(projectId)) {
                if (employeeService.isEmployeeExists(employeeId)) {
                    if (projectService.isAlreadyAssigned(employeeId,
                            projectId)) {
                        project = projectService.showSingleProject(projectId);
                        employeeDto = employeeService
                                .showSingleEmployee(employeeId);
                        employees = project.getEmployees();

                        for (EmployeeDto employee : employees) {
                            if (employeeId == employee.getId()) {
                                employees.remove(employee);
                                break;
                            }
                        }
                        project.setEmployees(employees);
                        projectService.updateProject(project);
                        redirectAttribute.addAttribute("message",
                                "Employee Unassigned");
                        redirectAttribute.addAttribute("link",
                                "AssignOrUnassignProject.jsp");
                        response = "redirect:/Message.jsp";
                    } else {
                        logger.warn(Constants.PROJECT_NOT_ASSIGN);
                        redirectAttribute.addAttribute("message",
                                "Employee not assigned to the project");
                        redirectAttribute.addAttribute("link",
                                "AssignOrUnassignProject.jsp");
                        response = "redirect:/Message.jsp";
                    }
                } else {
                    logger.warn(Constants.ID_NOT_EXISTS);
                    redirectAttribute.addAttribute("message",
                            Constants.EMPLOYEE_NOT_EXISTS);
                    redirectAttribute.addAttribute("link",
                            "AssignOrUnassignProject.jsp");
                    response = "redirect:/Message.jsp";
                }
            } else {
                redirectAttribute.addAttribute("message",
                        Constants.PROJECT_NOT_EXISTS);
                redirectAttribute.addAttribute("link",
                        "AssignOrUnassignProject.jsp");
                response = "redirect:/Message.jsp";
            }
        } catch (EMSException emsException) {
            logger.error("error in Unassign project");
            redirectAttribute.addAttribute("error",
                    "error in unassign project");
            redirectAttribute.addAttribute("link",
                    "AssignOrUnassignProject.jsp");
        }
        return response;
    }
}