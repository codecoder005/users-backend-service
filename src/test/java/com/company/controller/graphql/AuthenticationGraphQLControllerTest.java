package com.company.controller.graphql;

import com.company.common.AppConstants;
import com.company.config.TestSecurityConfiguration;
import com.company.model.response.AuthenticationResponse;
import com.company.model.response.RegistrationResponse;
import com.company.service.impl.AuthenticationServiceImpl;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = {"unit-test"})
@Import(TestSecurityConfiguration.class)
@AutoConfigureGraphQlTester
class AuthenticationGraphQLControllerTest {
    @Autowired
    private GraphQlTester graphQlTester;

    @Autowired
    private Gson jsonHelper;

    @MockitoBean
    private AuthenticationServiceImpl authenticationService;

    @Test
    @DisplayName("Registration Success")
    void shouldRegisterUserSuccessfully() {
        // language=GraphQL
        String auth_register = """
        mutation auth_register($details: RegistrationRequest!) {
            auth {
                register(details: $details) {
                    ... registrationResponse
                }
            }
        }
        fragment registrationResponse on RegistrationResponse {
            status
            uid
            email
            registeredOn
        }
        """;

        Map<String, String> registrationRequest = Map.of(
                "name", "John Doe",
                "email", "john.doe@email.com",
                "password", "password",
                "roles", "user,admin"
        );

        UUID uid = UUID.randomUUID();

        RegistrationResponse registrationResponse = RegistrationResponse.builder()
                .status(AppConstants.Values.SUCCESS_STRING_UPPER)
                .uid(uid)
                .email("john.doe@email.com")
                .build();
        when(authenticationService.register(any()))
                .thenReturn(registrationResponse);
        GraphQlTester.Entity<RegistrationResponse, ?> response = graphQlTester.document(auth_register)
                .variable("details", registrationRequest)
                .execute()
                .path("auth.register")
                .entity(RegistrationResponse.class);

        assertEquals(AppConstants.Values.SUCCESS_STRING_UPPER, response.get().getStatus());
        assertEquals(uid, response.get().getUid());
        assertEquals("john.doe@email.com", response.get().getEmail());
    }

    @Test
    @DisplayName("Login Success")
    void shouldLoginSuccessfully() {
        // language=GraphQL
        String login_mutation = """
        mutation auth_login($userEmail:String!, $authString:String!) {
            auth {
                login(credentials: {
                    email: $userEmail
                    password: $authString
                }) {
                    ... authenticationResponse
                }
            }
        }
        fragment authenticationResponse on AuthenticationResponse {
            uid
            name
            email
            roles
        }
        """;
        UUID uid = UUID.randomUUID();
        when(authenticationService.login(any()))
                .thenReturn(
                        AuthenticationResponse.builder()
                                .uid(uid)
                                .name("John Doe")
                                .email("john.doe@email.com")
                                .roles(List.of("user", "admin"))
                                .build()
                );

        GraphQlTester.Entity<AuthenticationResponse, ?> response = graphQlTester.document(login_mutation)
                .variable("userEmail", "john.doe@email.com")
                .variable("authString", "password")
                .execute()
                .path("auth.login")
                .entity(AuthenticationResponse.class);

        assertEquals(uid, response.get().getUid());
        assertEquals("John Doe", response.get().getName());
        assertEquals("john.doe@email.com", response.get().getEmail());
        assertEquals(List.of("user", "admin"), response.get().getRoles());
    }
}
