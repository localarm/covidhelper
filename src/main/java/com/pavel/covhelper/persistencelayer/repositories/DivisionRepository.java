package com.pavel.covhelper.persistencelayer.repositories;

import com.pavel.covhelper.persistencelayer.entities.Division;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DivisionRepository extends JpaRepository<Division, Long> {

    @Query("Select distinct d FROM Division d LEFT JOIN FETCH d.employees e WHERE d.department.id=:id ORDER BY d.id, e.lastname")
    List<Division> getDivisionAndEmployeesByDepartmentId(long id);
}
