package com.pavel.covhelper.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class UnitDepartmentsAndCountsDTO {
    private String unitName;
    private Long unitId;
    private long plannedCount;
    private long startedCount;
    private long finishedCount;
    private long allCount;
    private List<DepartmentAndCountsDTO> departments;
}
