package com.pavel.covhelper.controllers;

import com.pavel.covhelper.dtos.UnitDepartmentsAndCountsDTO;
import com.pavel.covhelper.dtos.UnitsWithCounts;
import com.pavel.covhelper.services.DepartmentService;
import com.pavel.covhelper.services.UnitNotFoundException;
import com.pavel.covhelper.services.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UnitController {

    private final UnitService unitService;
    private final DepartmentService departmentService;

    @Autowired
    public UnitController(UnitService unitService, DepartmentService departmentService) {
        this.unitService = unitService;
        this.departmentService = departmentService;
    }

    @RequestMapping(path = "units", method = RequestMethod.GET)
    ModelAndView getUnitsStats(){
        UnitsWithCounts unitAndCounts =  unitService.loadUnitsAndCounts();
        return new ModelAndView("units", "unitsWithCounts",unitAndCounts);
    }

    @RequestMapping(path = "unit/{id}", method = RequestMethod.GET)
    public ModelAndView getDepartments(@PathVariable(value = "id") long unitId,
                                       @RequestParam(value = "errorMsg", required = false) String errorMsg)
            throws UnitNotFoundException {
        UnitDepartmentsAndCountsDTO departmentDTO = departmentService.getDepartmentsByUnitId(unitId);
        ModelAndView modelAndView = new ModelAndView("departments");
        if (errorMsg !=null) {
            modelAndView.addObject( "errorMsg", errorMsg);
        }
        modelAndView.addObject( "departmentsWithCounts", departmentDTO);
        return modelAndView;
    }
}
