package com.pavel.covhelper.services;

import com.pavel.covhelper.persistencelayer.entities.Department;
import com.pavel.covhelper.persistencelayer.repositories.DepartmentRepository;
import com.pavel.covhelper.persistencelayer.entities.Division;
import com.pavel.covhelper.persistencelayer.repositories.DivisionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
public class DivisionServiceImpl implements DivisionService{
    private final static Logger LOGGER = LoggerFactory.getLogger(DivisionServiceImpl.class);
    private final DepartmentRepository departmentRepository;
    private final DivisionRepository divisionRepository;


    public DivisionServiceImpl(DepartmentRepository departmentRepository, DivisionRepository divisionRepository) {
        this.departmentRepository = departmentRepository;
        this.divisionRepository = divisionRepository;
    }

    @Override
    @Transactional
    public void addDivision(String divisionName, long departmentId) throws DepartmentNotFoundException, IllegalStateException{
        if (divisionName == null) {
            throw new IllegalStateException("divisionName must not be null");
        }
        try {
            Department department = departmentRepository.findById(departmentId)
                    .orElseThrow();
            Division division = new Division();
            division.setDepartment(department);
            division.setName(divisionName);
            divisionRepository.save(division);
        } catch (NoSuchElementException ex) {
            LOGGER.warn("Trying to access non existent department with {} id", departmentId);
            throw new DepartmentNotFoundException("Department with " + departmentId +" not exist");
        }
    }

    @Override
    public void deleteById(long divisionId) throws DivisionNotFoundException{
        try {
            divisionRepository.deleteById(divisionId);
        } catch(EmptyResultDataAccessException emptyResultDataAccessException) {
            LOGGER.warn("Try to delete non existent division with id = " +divisionId, emptyResultDataAccessException);
            throw new DivisionNotFoundException("division with " + divisionId +" not exist");
        }
    }
}
