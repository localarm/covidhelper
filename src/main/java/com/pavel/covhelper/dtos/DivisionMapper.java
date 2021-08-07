package com.pavel.covhelper.dtos;

import com.pavel.covhelper.persistencelayer.entities.Division;
import com.pavel.covhelper.persistencelayer.entities.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DivisionMapper {
    @Mapping(source = "id", target = "divisionId")
    DivisionDTO divisionToDto(Division division);

    EmployeeDTO employeeToDto(Employee employee);
}
