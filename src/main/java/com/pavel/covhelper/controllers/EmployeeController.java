package com.pavel.covhelper.controllers;

import com.pavel.covhelper.dtos.ChangeEmployeeForm;
import com.pavel.covhelper.dtos.NewEmployeeForm;
import com.pavel.covhelper.services.DepartmentNotFoundException;
import com.pavel.covhelper.services.DivisionNotFoundException;
import com.pavel.covhelper.services.EmployeeNotFoundException;
import com.pavel.covhelper.services.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;
import java.security.Principal;

@Controller
public class EmployeeController {

    private final static Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @RequestMapping(path = "/department/{id}/add_employee", method = RequestMethod.POST)
    public String addEmployee( @PathVariable("id") String departmentIdString, @Valid NewEmployeeForm employee,
                              BindingResult bindingResult, Principal principal,
                              RedirectAttributes redirectAttributes) throws
            DepartmentNotFoundException {
        long departmentId = checkDepartmentIdWithDepartmentNotFoundExceptionThrow(principal, departmentIdString);
        if (!bindingResult.hasErrors()) {
            try {
                employeeService.addEmployee(employee.getFirstname().trim(), employee.getSecondname().trim(),
                        employee.getLastname().trim(), employee.getPost().trim(), employee.getAddress().trim(),
                        employee.getStatus().trim(), employee.getDivisionId());
            } catch (DivisionNotFoundException ex) {
                redirectAttributes.addAttribute("errorMsg",
                        "отдел, к которому добавляется сотрудник не существует");
            }
        } else {
            LOGGER.warn("User {} pass wrong employee parameters when add employee to {} department. {}",principal.getName(),
                    departmentId, bindingResult.getFieldErrors());
            redirectAttributes.addAttribute("errorMsg",
                    "при добавлении сотрудника, все поля должны быть заполнены");
        }
        return "redirect:/department/" +departmentId;
    }

    @RequestMapping(path = "/department/{id}/delete_employee", method = RequestMethod.POST)
    public String deleteEmployee(@PathVariable("id") String departmentIdString, @RequestParam("empId") long employeeId,
                                 Principal principal, RedirectAttributes redirectAttributes)
            throws DepartmentNotFoundException {
        long departmentId = checkDepartmentIdWithDepartmentNotFoundExceptionThrow(principal, departmentIdString);
        try {
            employeeService.deleteById(employeeId);
        } catch (EmployeeNotFoundException ex) {
            redirectAttributes.addAttribute("errorMsg", "данного сотрудника не существует");
        }
        return "redirect:/department/"+departmentId;

    }

    @RequestMapping(path ="/department/{id}/update_employee", method = RequestMethod.POST)
    public String changeEmployeeInfo(@PathVariable("id") String departmentIdString, @Valid ChangeEmployeeForm employee,
                                     BindingResult bindingResult, Principal principal,
                                     RedirectAttributes redirectAttributes)
            throws DepartmentNotFoundException {
        long departmentId = checkDepartmentIdWithDepartmentNotFoundExceptionThrow(principal, departmentIdString);
        if (!bindingResult.hasErrors()) {
            try {
                employeeService.updateEmployee(employee.getFirstname().trim(), employee.getLastname().trim(),
                        employee.getSecondname().trim(), employee.getPost().trim(),
                        employee.getAddress().trim(), employee.getStatus().trim(), employee.getEmpId(),
                        employee.getDivId() );
            } catch (EmployeeNotFoundException ex) {
                redirectAttributes.addAttribute("errorMsg", "Такого сотрудника не существует");
            } catch (DivisionNotFoundException ex) {
                redirectAttributes.addAttribute("errorMsg",
                        "Выбранный отдел больше не существует");
            }
        } else {
            LOGGER.warn("User {} pass wrong parameters in POST to change employee info{}",principal.getName(),
                    bindingResult.getFieldErrors());
            redirectAttributes.addAttribute("errorMsg", "При изменении данных сотрудника все" +
                    " поля должны быть корректно заполнены");
        }
        return "redirect:/department/"+departmentId;

    }

    private long checkDepartmentIdWithDepartmentNotFoundExceptionThrow(Principal principal, String departmentId) throws
            DepartmentNotFoundException {
        try {
            return Long.parseLong(departmentId);
        }  catch (NumberFormatException ex) {
            LOGGER.warn("User {} pass non-digit department id = {} in POST request", principal, departmentId);
            throw new DepartmentNotFoundException();
        }
    }
}
