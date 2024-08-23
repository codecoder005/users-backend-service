package com.company.controller.rest;

import com.company.api.PingAPI;
import com.company.model.request.AuthenticationRequest;
import com.company.model.response.AuthenticationResponse;
import com.company.model.request.RegistrationRequest;
import com.company.model.response.PingAPIResponse;
import com.company.model.response.RegistrationResponse;
import com.company.service.AuthenticationService;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(
        name = "Authentication Controller",
        description = """
                The AuthenticationController class manages user authentication within the backend system, offering endpoints for user registration and login. 
                Through the /register endpoint, users can create new accounts by providing necessary registration details, while the /login endpoint validates user credentials, granting access to authenticated users. 
                These endpoints are designed to ensure secure access to the system, enhancing user experience and system security. 
                The class leverages Spring's validation mechanisms to ensure data integrity, providing a robust and reliable authentication mechanism for users interacting with the backend system.
                """,
        externalDocs = @ExternalDocumentation(
                url = "https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/"
        )
)
public class AuthenticationController implements PingAPI {
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

    @PostMapping(value = "/register", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RegistrationResponse> register(
            @Valid @RequestBody RegistrationRequest registrationRequest
    ) {
        log.info("AuthenticationController::register");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authenticationService.register(registrationRequest));
    }

    @PostMapping(value = "/login", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AuthenticationResponse> login(
            @Valid @RequestBody AuthenticationRequest credentials
    ) {
        log.info("AuthenticationController::login");
        return ResponseEntity.status(HttpStatus.OK)
                .body(authenticationService.login(credentials));
    }
}
