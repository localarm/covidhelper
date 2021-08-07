package com.pavel.covhelper.persistencelayer.repositories;

public interface UnitWithStats {
    String getName();
    long getId();
    long getStartedCount();
    long getPlannedCount();
    long getFinishedCount();
    long getAllCount();

}
