package com.company.exception;

public class TransactionNotFoundException extends AppException {
    public TransactionNotFoundException(String message) {
        super(message);
    }
}
