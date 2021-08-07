package com.pavel.covhelper.controllers;

import com.pavel.covhelper.security.UserAlreadyExistException;
import com.pavel.covhelper.services.DepartmentNotFoundException;
import com.pavel.covhelper.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegisterController {

    private final RegistrationService registrationService;

    @Autowired
    public RegisterController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @RequestMapping(path = "/department/{id}/register", method = RequestMethod.POST)
    private String registerViewer(@RequestParam("login") String login, @RequestParam("pass") String password,
                                @PathVariable("id") long departmentId, RedirectAttributes redirectAttributes)
            throws DepartmentNotFoundException {
        try {
            registrationService.registerDepartmentViewer(login, password, departmentId);
        } catch (UserAlreadyExistException e) {
            redirectAttributes.addAttribute("errorMsg", "аккаунт с таким логином уже существует");
        }
        return "redirect:/department/"+departmentId;
    }
}
