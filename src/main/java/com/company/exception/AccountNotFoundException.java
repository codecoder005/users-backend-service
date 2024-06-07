package com.company.exception;

public class AccountNotFoundException extends AppException{
    public AccountNotFoundException(String message) {
        super(message);
    }
}
