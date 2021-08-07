package com.pavel.covhelper.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ChangeEmployeeForm {
    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    @NotBlank
    private String secondname;
    private long divId;
    private long empId;
    @NotBlank
    private String post;
    @NotBlank
    private String address;
    @NotBlank
    private String status;


}
