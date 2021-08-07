package com.pavel.covhelper.dtos;

import lombok.Data;
import java.util.List;

@Data
public class DepartmentWithEmployeesDTO {
    private String departmentName;
    private long departmentId;
    private long plannedCount;
    private long startedCount;
    private long finishedCount;
    private long allCount;
    private final String[] statuses = {"не планируется", "планируется", "начата", "выполнена"};
    private final String defaultSelectedValue = "не планируется";
    private List<DivisionDTO> divisions;
}
