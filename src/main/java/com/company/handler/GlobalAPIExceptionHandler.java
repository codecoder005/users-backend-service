package com.company.handler;

import com.company.exception.AppException;
import com.company.exception.UserNotFoundException;
import com.company.model.response.ExceptionResponse;
import com.flagsmith.exceptions.FlagsmithClientError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalAPIExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ExceptionResponse> handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorMessage(ex.getMessage());
        exceptionResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exceptionResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ExceptionResponse> handleRuntimeException(RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorMessage(ex.getMessage());
        exceptionResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exceptionResponse);
    }

    @ExceptionHandler(FlagsmithClientError.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ResponseEntity<ExceptionResponse> handleFlagsmithClientError(FlagsmithClientError ex) {
        log.error("GlobalAPIExceptionHandler::FlagsmithClientError {}", ex.getMessage());
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorMessage(ex.getMessage());
        exceptionResponse.setStatusCode(HttpStatus.SERVICE_UNAVAILABLE.value());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exceptionResponse);
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<ExceptionResponse> handleHttpMessageConversionException(HttpMessageConversionException ex) {
        log.error(ex.getMessage(), ex);
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorMessage(ex.getMessage());
        exceptionResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exceptionResponse);
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ExceptionResponse> handleAppException(AppException e) {
        log.error("GlobalAPIExceptionHandler::AppException {}", e.getMessage());
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorMessage(e.getMessage());
        exceptionResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exceptionResponse);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ExceptionResponse> handleBindException(BindException ex) {
        log.error("GlobalAPIExceptionHandler::BindException {}", ex.getMessage());
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        Map<String, Object> errors = new LinkedHashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        exceptionResponse.setErrorMessage("One or more fields are failing validation criteria");
        exceptionResponse.setErrors(errors);
        exceptionResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exceptionResponse);
    }

    @ExceptionHandler(value = {
            UserNotFoundException.class
    })
    public ResponseEntity<ExceptionResponse> handleUserNotFoundException(UserNotFoundException e) {
        log.error("GlobalAPIExceptionHandler::UserNotFoundException {}", e.getMessage());
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorMessage(e.getMessage());
        exceptionResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exceptionResponse);
    }

    @ExceptionHandler(value = {AuthorizationDeniedException.class})
    public ResponseEntity<ExceptionResponse> handleAuthorizationDeniedException(AuthorizationDeniedException ex, WebRequest request) {
        log.error("GlobalAPIExceptionHandler::handleAuthorizationDeniedException {}", ex.getMessage());
        ex.printStackTrace();
        ExceptionResponse response = ExceptionResponse.builder()
                .errorMessage(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .errors(Map.of())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @ExceptionHandler(value = {ServletRequestBindingException.class})
    public ResponseEntity<ExceptionResponse> handleServletRequestBindingException(ServletRequestBindingException ex, WebRequest request) {
        log.error("GlobalAPIExceptionHandler::handleServletRequestBindingException {}", ex.getMessage());
        ex.printStackTrace();
        ExceptionResponse response = ExceptionResponse.builder()
                .errorMessage(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .errors(Map.of())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}
