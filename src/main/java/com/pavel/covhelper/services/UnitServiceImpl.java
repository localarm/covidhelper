package com.pavel.covhelper.services;

import com.pavel.covhelper.dtos.UnitsWithCounts;
import com.pavel.covhelper.persistencelayer.repositories.UnitRepository;
import com.pavel.covhelper.persistencelayer.repositories.UnitWithStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UnitServiceImpl implements UnitService{
    private final UnitRepository unitRepository;

    @Autowired
    public UnitServiceImpl(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    @Override
    public UnitsWithCounts loadUnitsAndCounts(){
        List<UnitWithStats> units = unitRepository.getAll();
        long planned = 0L;
        long started = 0L;
        long finished = 0L;
        long allCount = 0L;
        for (UnitWithStats unit : units) {
            planned = planned + unit.getPlannedCount();
            started = started + unit.getStartedCount();
            finished = finished + unit.getFinishedCount();
            allCount = allCount +unit.getAllCount();
        }
        return new UnitsWithCounts(planned, started, finished, allCount, units);
    }

}
