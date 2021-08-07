package com.pavel.covhelper.controllers;

import com.pavel.covhelper.dtos.DepartmentWithEmployeesDTO;
import com.pavel.covhelper.services.DepartmentService;
import com.pavel.covhelper.services.EmployeeService;
import com.pavel.covhelper.services.DepartmentNotFoundException;
import com.pavel.covhelper.services.UnitNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class DepartmentController {

    private final static Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);
    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    @RequestMapping(path = "/department/{id}", method = RequestMethod.GET)
    public ModelAndView getDepartment(Authentication authentication,
                                      @PathVariable(value = "id") String departmentIdString,
                @RequestParam(name = "errorMsg", required = false) String errorMsg)
                throws DepartmentNotFoundException {
        try {
            long departmentId = Long.parseLong(departmentIdString);
            // выбрасывает DepartmentNotFoundException если управления с айди нет
            DepartmentWithEmployeesDTO departmentWithEmployeesDTO =
                    employeeService.getEmployeesByDepartmentId(departmentId);
            ModelAndView modelAndView = new ModelAndView("department",
                    "department", departmentWithEmployeesDTO);
            //добавляет сообщение с ошибкой, если оно будет при редиректе
            if (errorMsg != null) {
                modelAndView.addObject("errorMsg", errorMsg);
            }
            // для отображения формы регистрации пользователя у админа
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_Admin"))) {
                modelAndView.addObject("admin", true);
            }
            return modelAndView;
        } catch (NumberFormatException ex) {
            throw new DepartmentNotFoundException();
        }
    }

    @RequestMapping(path = "/departments", method = RequestMethod.POST)
    public String addDepartment(@RequestParam("departmentName") String departmentName,
                                @RequestParam("unitId") String unitIdString, Principal principal,
                                RedirectAttributes redirectAttributes)
            throws UnitNotFoundException {
        try {
            long unitId = Long.parseLong(unitIdString);
            if (departmentName == null || departmentName.trim().length() == 0) {// не должно произойти в текущей версии сайта
                LOGGER.warn("User {} pass POST with wrong departmentName {} addDepartment request",
                        principal.getName(), departmentName);
                redirectAttributes.addAttribute("errorMsg", "Название должно состоять минимум из"+
                        " одного не пробельного символа");
            } else {
                departmentService.addDepartment(departmentName.trim(), unitId);
            }
            return "redirect:/unit/"+unitId;
        } catch (NumberFormatException ex) {// не должно произойти в текущей версии сайта
            LOGGER.warn("User {} did POST request with wrong unitId {} to addDepartment",principal.getName(), unitIdString);
            throw new UnitNotFoundException();
        }
    }

    @RequestMapping(path="/department/{id}/delete", method = RequestMethod.POST)
    public String deleteDepartment(@PathVariable("id") String departmentIdString, @RequestParam("unitId") long unitId,
                                   Principal principal, RedirectAttributes redirectAttributes)
            throws DepartmentNotFoundException {
        try {
            long departmentId = Long.parseLong(departmentIdString);
            departmentService.deleteDepartment(departmentId);
        } catch (DepartmentNotFoundException e) {
            redirectAttributes.addAttribute("errorMsg",
                    "Не удается найти управление/филиал, попробуйте еще раз");
        } catch (NumberFormatException ex) {// не должно произойти без пост запроса со стороны
            LOGGER.warn("User {} sent wrong department id = {} in POST delete_department request", principal.getName(),
                    departmentIdString);
            throw new DepartmentNotFoundException();
        }
        return "redirect:/unit/"+unitId;
    }

}
