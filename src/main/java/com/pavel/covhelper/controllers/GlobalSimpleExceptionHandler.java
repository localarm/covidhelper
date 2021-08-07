package com.pavel.covhelper.controllers;

import com.pavel.covhelper.services.DepartmentNotFoundException;
import com.pavel.covhelper.services.UnitNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalSimpleExceptionHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(GlobalSimpleExceptionHandler.class);

    @ExceptionHandler({NoHandlerFoundException.class, HttpRequestMethodNotSupportedException.class})
    public ModelAndView wrongPathHandle() {
        return new ModelAndView("my_error", "errorMsg", "Данной страницы не существует");
    }

    @ExceptionHandler(DepartmentNotFoundException.class)
    public ModelAndView wrongDepartmentErrorHandle() {
        return new ModelAndView("my_error", "errorMsg", "Такого управления/филиала не " +
                "существует");
    }

    @ExceptionHandler(UnitNotFoundException.class)
    public ModelAndView wrongUnitErrorHandle(Exception ex) {
        LOGGER.warn("Try to access non existent unit", ex);
        return new ModelAndView("my_error", "errorMsg", "Такого структурного " +
                "подразделения не существует");
    }


    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandle(Exception ex) {
        LOGGER.warn("Unexpected error occurred", ex);
        return new ModelAndView("my_error", "errorMsg", "Что-то пошло не так, попробуйте позднее");
    }
}
