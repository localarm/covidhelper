package com.pavel.covhelper.services;

import com.pavel.covhelper.dtos.DepartmentWithEmployeesDTO;
import com.pavel.covhelper.dtos.DivisionMapper;
import com.pavel.covhelper.dtos.DivisionDTO;
import com.pavel.covhelper.persistencelayer.entities.Division;
import com.pavel.covhelper.persistencelayer.entities.Employee;
import com.pavel.covhelper.persistencelayer.repositories.DepartmentRepository;
import com.pavel.covhelper.persistencelayer.repositories.DepartmentsCounts;
import com.pavel.covhelper.persistencelayer.repositories.DivisionRepository;
import com.pavel.covhelper.persistencelayer.repositories.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService{
    private final static Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final DivisionRepository divisionRepository;
    private final DivisionMapper divisionMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository,
                               DivisionRepository divisionRepository, DivisionMapper divisionMapper) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.divisionRepository = divisionRepository;
        this.divisionMapper = divisionMapper;
    }

    @Override
    @Transactional
    public DepartmentWithEmployeesDTO getEmployeesByDepartmentId(Long departmentId) throws DepartmentNotFoundException {
        DepartmentsCounts counts = departmentRepository.countStatusesById(departmentId);
        if(counts==null) {
            LOGGER.warn("Trying to acquire non existent department with {} id", departmentId);
            throw new DepartmentNotFoundException("No such department with" + departmentId + " id");
        }
        DepartmentWithEmployeesDTO departmentWithEmployeesDTO = new DepartmentWithEmployeesDTO();
        departmentWithEmployeesDTO.setDepartmentId(departmentId);
        departmentWithEmployeesDTO.setDepartmentName(counts.getDepartmentName());
        departmentWithEmployeesDTO.setAllCount(counts.getAllCount());
        departmentWithEmployeesDTO.setPlannedCount(counts.getPlannedCount());
        departmentWithEmployeesDTO.setFinishedCount(counts.getFinishedCount());
        departmentWithEmployeesDTO.setStartedCount(counts.getStartedCount());
        List<DivisionDTO> divisionWithEmployees = new ArrayList<>();
        departmentWithEmployeesDTO.setDivisions(divisionWithEmployees);
        List<Division> divisions = divisionRepository.getDivisionAndEmployeesByDepartmentId(departmentId);
        for (Division division: divisions) {
            divisionWithEmployees.add(divisionMapper.divisionToDto(division));
        }
        return departmentWithEmployeesDTO;
    }

    @Override
    public void deleteById(long employeeId) throws EmployeeNotFoundException {
        try {
            employeeRepository.deleteById(employeeId);
        } catch (EmptyResultDataAccessException ex) {
            throw new EmployeeNotFoundException("Employee with " + employeeId + " id does not exist");
        }
    }

    @Override
    @Transactional
    public void addEmployee(String firstname, String secondname, String lastname, String post, String address,
                            String status, long divisionId) throws DivisionNotFoundException {
        try {
            Division division = divisionRepository.findById(divisionId).orElseThrow();
            Employee employee = new Employee();
            employee.setFirstname(firstname);
            employee.setSecondname(secondname);
            employee.setLastname(lastname);
            employee.setPost(post);
            employee.setAddress(address);
            employee.setStatus(status);
            employee.setDivision(division);
            employeeRepository.save(employee);
        } catch (NoSuchElementException ex) {
            LOGGER.warn("Trying to add new Employee to non existent division with {} id", divisionId);
            throw new DivisionNotFoundException("division with " + divisionId + " id does not exist");
        }
    }

    @Override
    @Transactional
    public void updateEmployee(String lastname, String firstname, String secondname, String post, String address,
                                  String status, long employeeId, long divisionId) throws DivisionNotFoundException,
            EmployeeNotFoundException {
        try {
            Division division = divisionRepository.findById(divisionId).orElseThrow();
            try {
                Employee employee = employeeRepository.findById(employeeId).orElseThrow();
                employee.setStatus(status);
                employee.setLastname(lastname);
                employee.setSecondname(secondname);
                employee.setFirstname(firstname);
                employee.setAddress(address);
                employee.setPost(post);
                employee.setDivision(division);
            } catch (NoSuchElementException ex) {
                LOGGER.warn("Trying to delete Employee with wrong id = {}", employeeId);
                throw new EmployeeNotFoundException("Employee with " + employeeId + " id does not exist");
            }

        } catch (NoSuchElementException ex) {
            LOGGER.warn("Trying to delete Employee of non existent division with {} id", divisionId);
            throw new DivisionNotFoundException("Division with " + divisionId + " id does not exist");
        }
    }


}
