package com.company.controller.graphql;

import com.company.model.*;
import com.company.model.request.AuthenticationRequest;
import com.company.model.request.RegistrationRequest;
import com.company.model.response.AuthenticationResponse;
import com.company.model.response.RegistrationResponse;
import com.company.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthenticationGraphQLController {
    private final AuthenticationService authenticationService;

    @MutationMapping(name = "auth")
    public AuthenticationMutationOperations authMutation() {
        log.info("AuthenticationGraphQLController::authMutation");
        return new AuthenticationMutationOperations();
    }

    @SchemaMapping(typeName = "AuthenticationMutationOperations", field = "register")
    public RegistrationResponse register(
            @Valid @Argument("details") RegistrationRequest details
    ) {
        log.info("AuthenticationGraphQLController::Mutation::register");
        return authenticationService.register(details);
    }

    @SchemaMapping(typeName = "AuthenticationMutationOperations", field = "login")
    public AuthenticationResponse login(
            @Valid @Argument("credentials") AuthenticationRequest credentials
    ) {
        log.info("AuthenticationGraphQLController::Mutation::login");
        return authenticationService.login(credentials);
    }

}
