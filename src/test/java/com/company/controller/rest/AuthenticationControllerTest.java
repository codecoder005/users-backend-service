package com.company.controller.rest;

import com.company.common.AppConstants;
import com.company.config.TestSecurityConfiguration;
import com.company.handler.GlobalAPIExceptionHandler;
import com.company.model.request.AuthenticationRequest;
import com.company.model.response.AuthenticationResponse;
import com.company.model.request.RegistrationRequest;
import com.company.model.response.RegistrationResponse;
import com.company.service.impl.AuthenticationServiceImpl;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles(profiles = {"unit-test"})
@Import(TestSecurityConfiguration.class)
class AuthenticationControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private Gson jsonHelper;
    @Autowired
    private AuthenticationController authenticationController;
    @Autowired
    private GlobalAPIExceptionHandler globalAPIExceptionHandler;

    @Mock
    private AuthenticationServiceImpl authenticationService;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController)
                .setControllerAdvice(globalAPIExceptionHandler)
                .build();
    }

    @Test
    @DisplayName("Registration Success")
    void testShouldRegisterUserSuccessfully() throws Exception {
        UUID uid = UUID.randomUUID();
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .email("john.doe@email.com")
                .password("password")
                .name("John Doe")
                .roles("user,admin")
                .build();
        when(authenticationService.register(any()))
                .thenReturn(RegistrationResponse.builder()
                        .status(AppConstants.Values.SUCCESS_STRING_UPPER)
                        .uid(uid)
                        .email(registrationRequest.getEmail())
                        .build()
                );

        mockMvc.perform(
                post(AppConstants.API.REGISTRATION_API)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonHelper.toJson(registrationRequest))
                )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Email is required for registration")
    void shouldThrowValidationExceptionWhenEmailIsNullForRegistration() throws Exception {
        UUID uid = UUID.randomUUID();
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .password("password")
                .name("John Doe")
                .roles("user,admin")
                .build();

        mockMvc.perform(
                post(AppConstants.API.REGISTRATION_API)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonHelper.toJson(registrationRequest))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value("400"))
                .andExpect(jsonPath("$.errorMessage").value(AppConstants.ErrorMessage.FIELDS_VALIDATION_FAILING_MESSAGE))
                .andExpect(jsonPath("$.errors.email").value(AppConstants.ErrorMessage.EMAIL_REQUIRED));
    }

    @Test
    @DisplayName("Password is required for registration")
    void shouldThrowValidationExceptionWhenPasswordIsNullForRegistration() throws Exception {
        UUID uid = UUID.randomUUID();
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .email("john.doe@email.com")
                .name("John Doe")
                .roles("user,admin")
                .build();

        mockMvc.perform(
                        post(AppConstants.API.REGISTRATION_API)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(jsonHelper.toJson(registrationRequest))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value("400"))
                .andExpect(jsonPath("$.errorMessage").value(AppConstants.ErrorMessage.FIELDS_VALIDATION_FAILING_MESSAGE))
                .andExpect(jsonPath("$.errors.password").value(AppConstants.ErrorMessage.PASSWORD_REQUIRED));
    }

    @Test
    @DisplayName("Login Success")
    void shouldLoginSuccessfully() throws Exception {
        UUID uid = UUID.randomUUID();
        AuthenticationRequest credentials = AuthenticationRequest.builder()
                .email("john.doe@email.com")
                .password("password")
                .build();
        when(authenticationService.login(any()))
                .thenReturn(AuthenticationResponse.builder()
                        .email("john.doe@email.com")
                        .name("John Doe")
                        .uid(uid)
                        .roles(List.of("user", "admin"))
                        .build()
                );

        mockMvc.perform(
                post(AppConstants.API.LOGIN_API)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonHelper.toJson(credentials))
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Email is Required for log in")
    void shouldThrowValidationExceptionWhenEmailIsNull() throws Exception {
        AuthenticationRequest credentials = AuthenticationRequest.builder()
                .password("password")
                .build();

        mockMvc.perform(
                post(AppConstants.API.LOGIN_API)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonHelper.toJson(credentials))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value("400"))
                .andExpect(jsonPath("$.errorMessage").value(AppConstants.ErrorMessage.FIELDS_VALIDATION_FAILING_MESSAGE))
                .andExpect(jsonPath("$.errors.email").value(AppConstants.ErrorMessage.EMAIL_REQUIRED));
    }

    @Test
    @DisplayName("Password is Required for log in")
    void shouldThrowValidationExceptionWhenPasswordIsNull() throws Exception {
        AuthenticationRequest credentials = AuthenticationRequest.builder()
                .email("john.doe@email.com")
                .build();

        mockMvc.perform(
                        post(AppConstants.API.LOGIN_API)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(jsonHelper.toJson(credentials))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value("400"))
                .andExpect(jsonPath("$.errorMessage").value(AppConstants.ErrorMessage.FIELDS_VALIDATION_FAILING_MESSAGE))
                .andExpect(jsonPath("$.errors.password").value(AppConstants.ErrorMessage.PASSWORD_REQUIRED));
    }
}