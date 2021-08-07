package com.pavel.covhelper.persistencelayer.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Setter
@Getter
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "empl_seq_gen")
    @SequenceGenerator(name = "empl_seq_gen", sequenceName = "employees_id_seq", allocationSize = 1)
    private long id;
    private String firstname;
    private String lastname;
    private String secondname;
    private String address;
    private String post;
    private String status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "division_id")
    private Division division;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        return id == employee.id && Objects.equals(firstname, employee.firstname)
                && Objects.equals(lastname, employee.lastname) && Objects.equals(secondname, employee.secondname)
                && Objects.equals(address, employee.address) && Objects.equals(post, employee.post)
                && Objects.equals(status, employee.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname, secondname, address, post, status);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", secondname='" + secondname + '\'' +
                ", adress='" + address + '\'' +
                ", post='" + post + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
