package com.pavel.covhelper.persistencelayer.repositories;

import com.pavel.covhelper.persistencelayer.entities.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UnitRepository extends JpaRepository<Unit, Long> {

   @Query(value = "select u.Name as Name, u.Id as Id, count(CASE WHEN e.status LIKE 'начата' THEN 1 end) as StartedCount, " +
           "count(CASE WHEN e.status LIKE 'планируется' THEN 1 end) as PlannedCount, " +
           "count(CASE WHEN e.status LIKE 'выполнена' THEN 1 end) as FinishedCount, " +
           "count(CASE WHEN e.status LIKE 'не планируется' THEN 1 end) as DeniedCount, " +
           "count(e.status) as AllCount" +
   " from units u Left OUTER JOIN departments d on u.id = d.unit_id LEFT OUTER JOIN divisions div ON d.id = div.department_id" +
           " LEFT OUTER join employees e ON div.id = division_id GROUP BY u.id order by u.id", nativeQuery = true)
   List<UnitWithStats> getAll();

}
