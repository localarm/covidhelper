package com.pavel.covhelper.services;

import com.pavel.covhelper.dtos.UnitDepartmentsAndCountsDTO;
import com.pavel.covhelper.dtos.DepartmentAndCountsDTO;
import com.pavel.covhelper.persistencelayer.entities.Department;
import com.pavel.covhelper.persistencelayer.entities.Unit;
import com.pavel.covhelper.persistencelayer.repositories.DepartmentRepository;
import com.pavel.covhelper.persistencelayer.repositories.DepartmentsWithUnitNameAndStats;
import com.pavel.covhelper.persistencelayer.repositories.UnitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final static Logger LOGGER = LoggerFactory.getLogger(DepartmentServiceImpl.class);
    private final DepartmentRepository departmentRepository;
    private final UnitRepository unitRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository, UnitRepository unitRepository) {
        this.departmentRepository = departmentRepository;
        this.unitRepository = unitRepository;
    }

    @Override
    public UnitDepartmentsAndCountsDTO getDepartmentsByUnitId(long unitId) throws UnitNotFoundException {
        List<DepartmentsWithUnitNameAndStats> departments = departmentRepository.findAllByUnitId(unitId);
        if (departments.isEmpty()) {
            throw new UnitNotFoundException("Unit with "+ unitId +" id not exist");
        }
        long planned = 0L;
        long started = 0L;
        long finished = 0L;
        long allCount = 0L;
        String unitName = departments.get(0).getUnitName();
        List<DepartmentAndCountsDTO> departmentWithStats = new ArrayList<>();
        //проверка, чтобы убедиться что у юнита есть департаменты. Если их нет, то параметр departments.name у
        // первого элемента должен быть null
        if(departments.get(0).getName()!=null) {
            for (DepartmentsWithUnitNameAndStats department : departments) {
                planned = planned + department.getPlannedCount();
                started = started + department.getStartedCount();
                finished = finished + department.getFinishedCount();
                allCount = allCount +department.getAllCount();
                departmentWithStats.add(new DepartmentAndCountsDTO(department.getId(), department.getName(),
                    department.getAllCount(), department.getPlannedCount(), department.getStartedCount(),
                    department.getFinishedCount()));
            }
        }
        return new UnitDepartmentsAndCountsDTO(unitName, unitId,  planned, started, finished, allCount, departmentWithStats);
    }

    @Transactional
    public void addDepartment(String departmentName, long unitId) throws UnitNotFoundException {
        try {
            Unit unit = unitRepository.findById(unitId).orElseThrow();
            Department department = new Department();
            department.setUnit(unit);
            department.setName(departmentName);
            departmentRepository.save(department);
        } catch (NoSuchElementException ex) {
            throw new UnitNotFoundException("Unit with "+ unitId +" id not exist");
        }
    }

    @Override
    public void deleteDepartment(long departmentId) throws DepartmentNotFoundException {
        try {
            departmentRepository.deleteById(departmentId);
        } catch (EmptyResultDataAccessException ex) {
            throw new DepartmentNotFoundException("Department with " + departmentId + " id not exist");
        }
    }


}
