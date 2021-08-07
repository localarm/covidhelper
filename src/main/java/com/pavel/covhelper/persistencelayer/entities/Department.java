package com.pavel.covhelper.persistencelayer.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**Cущность, описывающая управление(название управления, его айди, СП к которому относится, отдел, которые входят в управление
 */
@Getter
@Setter
@Entity
@Table(name = "departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_gen")
    @SequenceGenerator(name="department_gen", sequenceName = "departments_id_seq", allocationSize = 1)
    public long id;
    public String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    public Unit unit;
    @OneToMany(mappedBy = "department",cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    public List<Division> divisions;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return id == that.id && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
