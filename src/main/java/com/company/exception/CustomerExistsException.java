package com.company.exception;

public class CustomerExistsException extends AppException{
    public CustomerExistsException(String message) {
        super(message);
    }
}
