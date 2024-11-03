package com.company.api;

import com.company.model.request.AuthenticationRequest;
import com.company.model.request.RegistrationRequest;
import com.company.model.response.AuthenticationResponse;
import com.company.model.response.RegistrationResponse;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
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
public interface AuthenticationAPI {
    @PostMapping(value = "/register", consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<RegistrationResponse> register(
            @Valid @RequestBody RegistrationRequest registrationRequest
    );

    @PostMapping(value = "/login", consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<AuthenticationResponse> login(
            @Valid @RequestBody AuthenticationRequest credentials
    );
}
