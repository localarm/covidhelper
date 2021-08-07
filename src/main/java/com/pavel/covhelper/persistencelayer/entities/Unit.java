package com.pavel.covhelper.persistencelayer.entities;

import com.pavel.covhelper.persistencelayer.entities.Department;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="units")
public class Unit {
    private String name;
    @Id
    private long id;
    @OneToMany(mappedBy = "unit", fetch = FetchType.LAZY)
    private List<Department> departments;
}
