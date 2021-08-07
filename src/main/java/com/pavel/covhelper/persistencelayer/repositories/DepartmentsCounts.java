package com.pavel.covhelper.persistencelayer.repositories;

public interface DepartmentsCounts {
     Long getStartedCount();
     Long getFinishedCount();
     Long getAllCount();
     Long getPlannedCount();
     String getDepartmentName();
}
