package com.pavel.covhelper.persistencelayer.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "divisions")
public class Division {
    @Id
    @GeneratedValue(generator = "division_gen")
    @SequenceGenerator(name="division_gen", sequenceName = "divisions_id_seq", allocationSize = 1)
    public long id;
    public String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    public Department department;
    @OneToMany(mappedBy = "division", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    public List<Employee> employees;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Division division = (Division) o;
        return id == division.id && name.equals(division.name) && department.equals(division.department) && Objects.equals(employees, division.employees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, department, employees);
    }

    @Override
    public String toString() {
        return "Division{" +
                "id=" + id +
                ", name='" + name;
    }
}
