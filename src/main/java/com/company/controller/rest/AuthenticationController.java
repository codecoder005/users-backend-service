package com.company.controller.rest;

import com.company.api.AuthenticationAPI;
import com.company.api.PingAPI;
import com.company.model.request.AuthenticationRequest;
import com.company.model.request.RegistrationRequest;
import com.company.model.response.AuthenticationResponse;
import com.company.model.response.PingAPIResponse;
import com.company.model.response.RegistrationResponse;
import com.company.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController implements PingAPI, AuthenticationAPI {
    private final AuthenticationService authenticationService;

    @Override
    public PingAPIResponse ping() {
        log.info("AuthenticationController::ping");
        return PingAPIResponse.builder()
                .status(200)
                .message("Up and Healthy")
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Override
    public ResponseEntity<RegistrationResponse> register(RegistrationRequest registrationRequest) {
        log.info("AuthenticationController::register");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authenticationService.register(registrationRequest));
    }

    @Override
    public ResponseEntity<AuthenticationResponse> login(AuthenticationRequest credentials) {
        log.info("AuthenticationController::login");
        return ResponseEntity.status(HttpStatus.OK)
                .body(authenticationService.login(credentials));
    }
}