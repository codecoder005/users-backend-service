package com.company.handler;

import com.company.exception.*;
import com.company.model.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class BankingAPIExceptionHandler {
    @ExceptionHandler(value = {FeatureDisabledException.class})
    public ResponseEntity<ExceptionResponse> handleFeatureDisabledException(FeatureDisabledException e) {
        log.error("BankingAPIExceptionHandler::handleFeatureDisabledException {}", e.getMessage());
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorMessage(e.getMessage());
        exceptionResponse.setStatusCode(HttpStatus.SERVICE_UNAVAILABLE.value());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(exceptionResponse);
    }

    @ExceptionHandler(value = {CustomerNotFoundException.class})
    public ResponseEntity<ExceptionResponse> handleCustomerNotFoundException(CustomerNotFoundException e) {
        log.error("BankingAPIExceptionHandler::handleCustomerNotFoundException {}", e.getMessage());
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorMessage(e.getMessage());
        exceptionResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    @ExceptionHandler(value = {CustomerExistsException.class})
    public ResponseEntity<ExceptionResponse> handleCustomerExistsException(CustomerExistsException e) {
        log.error("BankingAPIExceptionHandler::handleCustomerExistsException {}", e.getMessage());
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorMessage(e.getMessage());
        exceptionResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    @ExceptionHandler(value = {AccountNotFoundException.class})
    public ResponseEntity<ExceptionResponse> handleAccountNotFoundException(AccountNotFoundException e) {
        log.error("BankingAPIExceptionHandler::handleAccountNotFoundException {}", e.getMessage());
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorMessage(e.getMessage());
        exceptionResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    @ExceptionHandler(value = {AccountExistsException.class})
    public ResponseEntity<ExceptionResponse> handleAccountExistsException(AccountExistsException e) {
        log.error("BankingAPIExceptionHandler::handleAccountExistsException {}", e.getMessage());
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorMessage(e.getMessage());
        exceptionResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }
}
