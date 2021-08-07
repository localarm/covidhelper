package com.pavel.covhelper.dtos;

import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

@Data
public class NewEmployeeForm {
    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    @NotBlank
    private String secondname;
    //TODO
    private long divisionId;
    @NotBlank
    private String post;
    @NotBlank
    private String address;
    //TODO
    private String status;


}
