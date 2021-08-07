package com.pavel.covhelper.dtos;

import com.pavel.covhelper.persistencelayer.repositories.UnitWithStats;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UnitsWithCounts {
    private long plannedCount;
    private long startedCount;
    private long finishedCount;
    private long allCount;
    private List<UnitWithStats> units;

}
