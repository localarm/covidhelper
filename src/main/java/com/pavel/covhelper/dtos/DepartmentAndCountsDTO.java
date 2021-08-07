package com.pavel.covhelper.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DepartmentAndCountsDTO {
    private long id;
    private String name;
    private long allCount;
    private long plannedCount;
    private long startedCount;
    private long finishedCount;
}
