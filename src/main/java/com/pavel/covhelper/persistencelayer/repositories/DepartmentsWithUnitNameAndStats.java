package com.pavel.covhelper.persistencelayer.repositories;


public interface DepartmentsWithUnitNameAndStats {
    String getName();
    String getUnitName();
    Long getUnitId();
    long getId();
    long getAllCount();
    long getPlannedCount();
    long getStartedCount();
    long getFinishedCount();
}
