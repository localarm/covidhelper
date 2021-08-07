package com.pavel.covhelper.services;

public class DepartmentNotFoundException extends Exception{
    public DepartmentNotFoundException() {
        super();
    }

    public DepartmentNotFoundException(String message) {
        super(message);
    }
}
