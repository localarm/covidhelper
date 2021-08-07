package com.pavel.covhelper.dtos;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class EmployeeDTO {
    private long id;
    private String firstname;
    private String lastname;
    private String secondname;
    private String address;
    private String post;
    private String status;
}
