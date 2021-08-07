package com.pavel.covhelper.persistencelayer.repositories;

import com.pavel.covhelper.persistencelayer.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
