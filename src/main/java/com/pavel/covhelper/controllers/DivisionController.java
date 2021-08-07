package com.pavel.covhelper.controllers;

import com.pavel.covhelper.services.DepartmentNotFoundException;
import com.pavel.covhelper.services.DivisionNotFoundException;
import com.pavel.covhelper.services.DivisionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@Validated
public class DivisionController {

    private final static Logger LOGGER = LoggerFactory.getLogger(DivisionController.class);
    private final DivisionService divisionService;

    public DivisionController(DivisionService divisionService) {
        this.divisionService = divisionService;
    }

    @RequestMapping(path= "department/{id}/add_division", method = RequestMethod.POST)
    public String addDivision(@PathVariable("id") String departmentIdString,
                              @RequestParam("divisionName") String divisionName, Principal principal,
                              RedirectAttributes redirectAttributes)
            throws DepartmentNotFoundException {
        long departmentId = checkDepartmentIdWithDepartmentNotFoundExceptionThrow(principal, departmentIdString);
        if (divisionName == null || divisionName.trim().length() == 0) {
            redirectAttributes.addAttribute("errorMsg",
                    "Название должно состоять хотя бы из одного не пробельного символа");
        } else {
            divisionService.addDivision(divisionName.trim(), departmentId);
        }
        return "redirect:/department/" + departmentId;
    }

    @RequestMapping(path = "department/{id}/delete_division", method = RequestMethod.POST)
    public String deleteDivision(@RequestParam("divId") long divisionId, @PathVariable("id") String departmentIdString,
                                 Principal principal, RedirectAttributes redirectAttributes)
            throws DepartmentNotFoundException {
        long departmentId = checkDepartmentIdWithDepartmentNotFoundExceptionThrow(principal, departmentIdString);
        try {
        divisionService.deleteById(divisionId);
        } catch (DivisionNotFoundException ex) {
            redirectAttributes.addAttribute("errorMsg",
                    "не удается удалить такой отдел,так как его не существует");
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
