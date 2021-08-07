package com.pavel.covhelper.services;

public class DivisionNotFoundException extends Exception{
    public DivisionNotFoundException() {
    }

    public DivisionNotFoundException(String message) {
        super(message);
    }

    public DivisionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
