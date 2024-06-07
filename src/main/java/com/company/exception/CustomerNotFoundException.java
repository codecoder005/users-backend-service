package com.company.exception;

public class CustomerNotFoundException extends AppException{
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
