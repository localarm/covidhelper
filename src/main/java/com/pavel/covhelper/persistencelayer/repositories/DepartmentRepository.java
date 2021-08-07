package com.pavel.covhelper.persistencelayer.repositories;

import com.pavel.covhelper.persistencelayer.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    @Query(value = "SELECT d.id as Id, d.name AS Name, u.name AS UnitName, u.id as UnitId, COUNT(e.status) AS AllCount, " +
            "COUNT(CASE WHEN e.status LIKE 'планируется' THEN 1 END) AS PlannedCount, " +
            "COUNT(CASE WHEN e. Status LIKE 'начата' THEN 1 END) AS StartedCount, " +
            "COUNT(CASE WHEN e.status LIKE 'выполнена' THEN 1 END) AS FinishedCount " +
            "FROM units u " +
            "LEFT JOIN departments d on u.id = d.unit_id LEFT JOIN divisions div on d.id = div.department_id " +
            "LEFT JOIN employees e ON div.id= e.division_id WHERE u.id = :id GROUP BY u.name, u.id, d.id, d.name ORDER BY d.id",
            nativeQuery = true)
    List<DepartmentsWithUnitNameAndStats> findAllByUnitId(@Param("id") Long unitId);

    @Query(value = "SELECT SUM(CASE WHEN e.status like 'начата' THEN 1 ELSE 0 END) AS PlannedCount, " +
            "SUM(CASE WHEN e.status like 'планируется' THEN 1 ELSE 0 END) AS StartedCount, " +
            "SUM(CASE WHEN e.status like 'выполнена' THEN 1 ELSE 0 END) AS FinishedCount, " +
            "COUNT(e.id) AS AllCount, dep.name as DepartmentName " +
            "FROM departments dep left join divisions div on dep.id = div.department_id" +
            " left join employees e on div.id = e.division_id WHERE dep.id = :id GROUP BY dep.name", nativeQuery = true)
    DepartmentsCounts countStatusesById(long id);
}
