/* 
 * Copyright (c) Sakthi Annamalai, Inc. All Rights Reserved.
 * 
 */
package com.ideas2it.employeemanagement.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;

import com.ideas2it.employeemanagement.dto.EmployeeDto;
import com.ideas2it.employeemanagement.dto.ProjectDto;
import com.ideas2it.employeemanagement.logger.LoggerFactory;
import com.ideas2it.employeemanagement.exceptions.EMSException;
import com.ideas2it.employeemanagement.service.EmployeeService;
import com.ideas2it.employeemanagement.service.ProjectService;
import com.ideas2it.employeemanagement.service.impl.ProjectServiceImpl;
import com.ideas2it.employeemanagement.constants.Constants;

/**
 * EmployeeController for employees in employee Management System
 *
 * @version 1.8.0_281
 * @author Sakthi Annamalai
 */
@Controller
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getInstance();

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    ProjectService projectService;

    /**
     * Creates object for EmployeeDto and send it to the EmployeeForm jsp
     * 
     * @param model The Model instance contains the request made by the client.
     * @return response as a jsp page
     */
    @GetMapping("/registerEmployee")
    private String registerEmployee(Model model) {
        EmployeeDto employeeDto = new EmployeeDto();
        model.addAttribute("employeeDto", employeeDto);
        model.addAttribute("title", "CREATE EMPLOYEE");
        model.addAttribute("formAction", "createEmployee");
        model.addAttribute("submit", "Create");

        return "EmployeeForm.jsp";
    }

    /**
     * Gets the employee details from the user in jsp page and sets it the
     * employeeDto object
     * 
     * @param employeeDto
     * @param model              The Model instance contains the request made by
     *                           the client.
     * @param redirectAttributes the RedirectAttribute instance for adding
     *                           messages.
     * @return response as a jsp page
     */
    @PostMapping("/createEmployee")
    private String insertEmployee(
            @ModelAttribute("employeeDto") EmployeeDto employeeDto, Model model,
            RedirectAttributes redirectAttribute) {
        String response = "redirect:/Error.jsp";

        try {

            if (employeeService
                    .isPhoneNumberExists(employeeDto.getPhoneNumber())) {
                logger.warn(Constants.PHONENUMBER_EXISTS);
                model.addAttribute("phoneMessage",
                        Constants.PHONENUMBER_EXISTS);
                return "EmployeeForm.jsp";
            }

            if (employeeService.isEmailExists(employeeDto.getEmailId())) {
                logger.warn(Constants.EMAILID_EXISTS);
                model.addAttribute("emailMessage", Constants.EMAILID_EXISTS);
                return "EmployeeForm.jsp";
            }

            employeeService.createEmployees(employeeDto);
            redirectAttribute.addAttribute("message",
                    Constants.EMPLOYEE_CREATE);
            redirectAttribute.addAttribute("link", "EmployeeMainMenu.jsp");
            response = "redirect:/Message.jsp";
        } catch (EMSException emsException) {
            logger.error(Constants.ERROR_IN_EMAILID);
            redirectAttribute.addAttribute("error", Constants.ERROR_IN_CREATE);
            redirectAttribute.addAttribute("link", "EmployeeMainMenu.jsp");
        }
        return response;
    }

    /**
     * Gets the list of employees from database and forward the response to
     * ViewAllEmployee jsp page
     * 
     * @param model              The Model instance contains the request made by
     *                           the client.
     * @param redirectAttributes the RedirectAttribute instance for adding
     *                           messages.
     * @return response as a jsp page
     */
    @GetMapping("/viewAllEmployee")
    private String showAllEmployees(Model model,
            RedirectAttributes redirectAttribute) {
        String response = "redirect:/Error.jsp";

        try {
            if (employeeService.checkDataBaseEmpty()) {
                redirectAttribute.addAttribute("message", "Database is Empty");
                redirectAttribute.addAttribute("link", "ViewEmployeeMenu.jsp");
                response = "redirect:/Message.jsp";
            } else {
                List<EmployeeDto> employees = employeeService.getAllEmployees();
                model.addAttribute("employees", employees);
                response = "ViewAllEmployee.jsp";
            }
        } catch (EMSException emsException) {
            logger.error(Constants.ERROR_IN_SHOWALL);
            redirectAttribute.addAttribute("error", Constants.EMAILID_EXISTS);
            redirectAttribute.addAttribute("link", "ViewEmployeeMenu.jsp");
        }
        return response;
    }

    /**
     * Gets the particular employee with corresponding project and forwards the
     * obtained project to DisplayEmployeeProject jsp page
     * 
     * @param id
     * @param model              The Model instance contains the request made by
     *                           the client.
     * @param redirectAttributes the RedirectAttribute instance for adding
     *                           messages.
     * @return response as a jsp page
     */
    @GetMapping("/displayEmployeeProject")
    private String employeeProject(@RequestParam("id") int id, Model model,
            RedirectAttributes redirectAttribute) {
        String response = "redirect:/Error.jsp";

        try {
            EmployeeDto employee = employeeService.showSingleEmployee(id);
            model.addAttribute("projects", employee.getProjects());
            response = "DisplayEmployeeProject.jsp";
        } catch (EMSException emsException) {
            logger.error("Error in displaying Employee Project");
            redirectAttribute.addAttribute("error",
                    "Error in showing Employee Project");
            redirectAttribute.addAttribute("link", "ViewEmployeeMenu.jsp");
        }
        return response;
    }

    /**
     * Gets the particular employee with corresponding employee id and forwards
     * the obtained employee object to ViewAllEmployee jsp page
     * 
     * @param id
     * @param model              The Model instance contains the request made by
     *                           the client.
     * @param redirectAttributes the RedirectAttribute instance for adding
     *                           messages.
     * @return response as a jsp page
     */
    @GetMapping("/viewSingleEmployee")
    private String showSingleEmployee(@RequestParam("id") int id, Model model,
            RedirectAttributes redirectAttribute) {
        String response = "redirect:/Error.jsp";

        try {
            if (employeeService.isEmployeeExists(id)) {
                List<EmployeeDto> employees = new ArrayList<EmployeeDto>();
                EmployeeDto employee = employeeService.showSingleEmployee(id);
                employees.add(employee);
                model.addAttribute("employees", employees);
                response = "ViewAllEmployee.jsp";
            } else {
                model.addAttribute("message", Constants.ID_NOT_EXISTS);

                response = "ViewSingleEmployee.jsp";
            }
        } catch (EMSException emsException) {
            logger.error(Constants.ERROR_IN_SHOWSINGLE);
            redirectAttribute.addAttribute("error",
                    "Error in showing Employee Project");
            redirectAttribute.addAttribute("link", "ViewEmployeeMenu.jsp");
        }
        return response;
    }

    /**
     * Deletes all employee in the database
     * 
     * @param redirectAttribute
     * @return response as a jsp page
     */
    @GetMapping("/deleteAllEmployee")
    private String deleteAllEmployee(RedirectAttributes redirectAttribute) {
        String response = "redirect:/Error.jsp";
        try {
            if (employeeService.deleteAllEmployees()) {
                redirectAttribute.addAttribute("message",
                        Constants.EMPLOYEE_DELETE_ALL);
                redirectAttribute.addAttribute("link", "EmployeeMainMenu.jsp");
                response = "redirect:/Message.jsp";
            } else {
                redirectAttribute.addAttribute("message",
                        Constants.EMPLOYEE_NOT_DELETE);
                redirectAttribute.addAttribute("link", "EmployeeMainMenu.jsp");
                response = "redirect:/Message.jsp";
            }
        } catch (EMSException emsException) {
            logger.error("Error in delete all Employee");
            redirectAttribute.addAttribute("error",
                    Constants.ERROR_IN_DELETEALL);
        }
        return response;
    }

    /**
     * Deletes the particular employee
     * 
     * @param id
     * @param redirectAttributes the RedirectAttribute instance for adding
     *                           messages.
     * @return response as a jsp page
     */
    @GetMapping("/deleteSingleEmployee")
    private String deleteSingleEmployee(@RequestParam("id") int id,
            RedirectAttributes redirectAttribute) {
        String response = "redirect:/Error.jsp";
        try {
            employeeService.deleteSingleEmployee(id);
            redirectAttribute.addAttribute("message",
                    "Employee Deleted Successfully");
            redirectAttribute.addAttribute("link", "ViewEmployeeMenu.jsp");
            response = "redirect:/Message.jsp";
        } catch (EMSException emsException) {
            logger.error(Constants.ERROR_IN_DELETESINGLE);
            redirectAttribute.addAttribute("message",
                    Constants.ERROR_IN_DELETESINGLE);
            redirectAttribute.addAttribute("link", "ViewEmployeeMenu.jsp");
        }
        return response;
    }

    /**
     * Gets id from user and gets the particular object and forwards the
     * obtained employee object to EmployeeForm jsp page
     * 
     * @param id
     * @param model              The Model instance contains the request made by
     *                           the client.
     * @param redirectAttributes the RedirectAttribute instance for adding
     *                           messages.
     * @return response as a jsp page
     */
    @GetMapping("/updateEmployeeById")
    private String updateEmployeeById(@RequestParam("id") int id, Model model,
            RedirectAttributes redirectAttribute) {
        String response = "redirect:/Error.jsp";
        EmployeeDto employeeDto;
        try {
            employeeDto = employeeService.showSingleEmployee(id);
            model.addAttribute("employeeDto", employeeDto);
            model.addAttribute("title", "UPDATE EMPLOYEE");
            model.addAttribute("formAction", "updateEmployee");
            model.addAttribute("submit", "Update");
            response = "EmployeeForm.jsp";
        } catch (EMSException emsException) {
            logger.error("Error in getting EmployeeId for project");
            redirectAttribute.addAttribute("message",
                    Constants.ERROR_IN_UPDATE_EMPLOYEE);
            redirectAttribute.addAttribute("link", "ViewEmployeeMenu.jsp");
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
    @PostMapping("/updateEmployee")
    private String updateEmployee(
            @ModelAttribute("employeeDto") EmployeeDto employeeDto,
            RedirectAttributes redirectAttribute) {
        String response = "redirect:/Error.jsp";
        try {
            int id = employeeDto.getId();
            if (employeeService.isDuplicateEmail(employeeDto.getEmailId(),
                    id)) {
                logger.warn(Constants.EMAILID_EXISTS);
                redirectAttribute.addAttribute("phoneMessage",
                        Constants.EMAILID_EXISTS);
                return "EmployeeForm.jsp";
            }
            if (employeeService
                    .isDuplicatePhoneNumber(employeeDto.getPhoneNumber(), id)) {
                logger.warn(Constants.PHONENUMBER_EXISTS);
                redirectAttribute.addAttribute("emailMessage",
                        Constants.PHONENUMBER_EXISTS);
                return "EmployeeForm.jsp";
            }
            EmployeeDto employee = employeeService.showSingleEmployee(id);
            employeeDto.setProjects(employee.getProjects());
            employeeService.updateEmployee(employeeDto);
            redirectAttribute.addAttribute("message",
                    "Employee updated successfully");
            redirectAttribute.addAttribute("link", "ViewEmployeeMenu.jsp");
            response = "redirect:Message.jsp";
        } catch (EMSException exception) {
            logger.error("Error in Update Employee");
            redirectAttribute.addAttribute("error",
                    Constants.ERROR_IN_UPDATE_EMPLOYEE);
            redirectAttribute.addAttribute("link", "ViewEmployeeMenu.jsp");
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
    @GetMapping("/assignForm")
    private String getAssignForm(Model model,
            RedirectAttributes redirectAttribute) {
        String response = "rediect:/Error.jsp";
        try {
            if (projectService.checkDataBaseEmpty()) {
                redirectAttribute.addAttribute("message", "Database is empty");
                redirectAttribute.addAttribute("link",
                        "AssignOrUnassignMenu.jsp");
                response = "redirect:Message.jsp";
            } else {
                model.addAttribute("title", "ASSIGN PROJECT");
                model.addAttribute("formAction", "assignProject");
                response = "AssignOrUnassignEmployee.jsp";
            }
        } catch (EMSException emsException) {
            logger.error("Error in getting assign form");
            redirectAttribute.addAttribute("error", "error in  assign form");
        }
        return response;
    }

    /**
     * Gets employeeId and projectId from user and assign projects to employees
     * 
     * @param employeeId
     * @param projectId
     * @param model              The Model instance contains the request made by
     *                           the client.
     * @param redirectAttributes the RedirectAttribute instance for adding
     *                           messages.
     * @return response as a jsp page
     */
    @PostMapping("/assignProject")
    private String assignProject(@RequestParam("employeeId") int employeeId,
            @RequestParam("projectId") int projectId, Model model,
            RedirectAttributes redirectAttribute) {

        String response = "redirect:/Error.jsp";
        EmployeeDto employee;
        ProjectDto projectDto;
        Set<ProjectDto> projects;

        try {
            if (employeeService.isEmployeeExists(employeeId)) {
                if (projectService.isProjectExists(projectId)) {

                    if (employeeService.isAlreadyAssigned(employeeId,
                            projectId)) {
                        redirectAttribute.addAttribute("message",
                                Constants.ALREADY_ASSIGN);
                        redirectAttribute.addAttribute("link",
                                "AssignOrUnassignMenu.jsp");
                        response = "redirect:/Message.jsp";
                    } else {
                        employee = employeeService
                                .showSingleEmployee(employeeId);
                        projectDto = projectService
                                .showSingleProject(projectId);
                        projects = employee.getProjects();
                        projects.add(projectDto);
                        employee.setProjects(projects);
                        employeeService.updateEmployee(employee);
                        redirectAttribute.addAttribute("message",
                                Constants.ASSIGN_PROJECT);
                        redirectAttribute.addAttribute("link",
                                "AssignOrUnassignMenu.jsp");
                        response = "redirect:/Message.jsp";
                    }
                } else {
                    logger.warn(Constants.PROJECT_NOT_EXISTS);
                    redirectAttribute.addAttribute("message",
                            Constants.PROJECT_NOT_EXISTS);
                    redirectAttribute.addAttribute("link",
                            "AssignOrUnassignMenu.jsp");
                    response = "redirect:/Message.jsp";
                }
            } else {
                redirectAttribute.addAttribute("message",
                        Constants.EMPLOYEE_NOT_EXISTS);
                redirectAttribute.addAttribute("link",
                        "AssignOrUnassignMenu.jsp");
                response = "redirect:/Message.jsp";
            }
        } catch (EMSException emsException) {
            logger.error(Constants.ERROR_IN_ASSIGNPROJECT);
            redirectAttribute.addAttribute("error",
                    Constants.ERROR_IN_ASSIGNPROJECT);
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
    @GetMapping("/unAssignForm")
    private String getUnAssignForm(Model model,
            RedirectAttributes redirectAttribute) {
        String response = "rediect:/Error.jsp";
        try {
            if (projectService.checkDataBaseEmpty()) {
                redirectAttribute.addAttribute("message", "Database is empty");
                redirectAttribute.addAttribute("link",
                        "AssignOrUnassignMenu.jsp");
                response = "redirect:Message.jsp";
            } else {
                model.addAttribute("title", "UNASSIGN PROJECT");
                model.addAttribute("formAction", "unAssignProject");
                response = "AssignOrUnassignEmployee.jsp";
            }
        } catch (EMSException emsException) {
            logger.error("Error in getting unassign form");
            redirectAttribute.addAttribute("error", "error in  assign form");
            redirectAttribute.addAttribute("link", "AssignOrUnassignMenu.jsp");
        }
        return response;
    }

    /**
     * Gets employeeId and projectId from user and unassign projects to
     * employees
     * 
     * @param employeeId
     * @param projectId
     * @param model              The Model instance contains the request made by
     *                           the client.
     * @param redirectAttributes the RedirectAttribute instance for adding
     *                           messages.
     * @return response as a jsp page
     */
    @PostMapping("/unAssignProject")
    private String unAssignProject(@RequestParam("employeeId") int employeeId,
            @RequestParam("projectId") int projectId, Model model,
            RedirectAttributes redirectAttribute) {

        String response = "redirect:/Error.jsp";
        EmployeeDto employee;
        ProjectDto projectDto;
        Set<ProjectDto> projects;

        try {

            if (employeeService.isEmployeeExists(employeeId)) {
                if (projectService.isProjectExists(projectId)) {
                    if (employeeService.isAlreadyAssigned(employeeId,
                            projectId)) {
                        employee = employeeService
                                .showSingleEmployee(employeeId);
                        projectDto = projectService
                                .showSingleProject(projectId);
                        projects = employee.getProjects();

                        for (ProjectDto project : projects) {
                            if (projectId == project.getId()) {
                                projects.remove(project);
                                break;
                            }
                        }
                        employee.setProjects(projects);
                        employeeService.updateEmployee(employee);
                        redirectAttribute.addAttribute("message",
                                Constants.PROJECT_UNASSIGNED);
                        redirectAttribute.addAttribute("link",
                                "AssignOrUnassignMenu.jsp");
                        response = "redirect:/Message.jsp";
                    } else {
                        logger.warn(Constants.PROJECT_NOT_ASSIGN);
                        redirectAttribute.addAttribute("message",
                                Constants.PROJECT_NOT_UNASSIGNED);
                        redirectAttribute.addAttribute("link",
                                "AssignOrUnassignMenu.jsp");
                        response = "redirect:/Message.jsp";
                    }
                } else {
                    redirectAttribute.addAttribute("message",
                            Constants.PROJECT_NOT_EXISTS);
                    redirectAttribute.addAttribute("link",
                            "AssignOrUnassignMenu.jsp");
                    response = "redirect:/Message.jsp";
                }
            } else {
                logger.warn(Constants.ID_NOT_EXISTS);
                redirectAttribute.addAttribute("message",
                        Constants.EMPLOYEE_NOT_EXISTS);
                redirectAttribute.addAttribute("link",
                        "AssignOrUnassignMenu.jsp");
                response = "redirect:/Message.jsp";
            }
        } catch (EMSException emsException) {
            logger.error(Constants.ERROR_IN_UNASSIGNPROJECT);
            redirectAttribute.addAttribute("error",
                    Constants.ERROR_IN_UNASSIGNPROJECT);
            redirectAttribute.addAttribute("link", "AssignOrUnassignMenu.jsp");
        }
        return response;
    }
}
