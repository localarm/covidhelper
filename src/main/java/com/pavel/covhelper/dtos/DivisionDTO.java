package com.pavel.covhelper.dtos;

import lombok.Data;

import java.util.List;

@Data
public class DivisionDTO {
    private long divisionId;
    private String name;
    private List<EmployeeDTO> employees;
}
