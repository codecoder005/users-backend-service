package com.company.service.impl;

import com.company.config.TestSecurityConfiguration;
import com.company.entity.UserEntity;
import com.company.exception.UserNotFoundException;
import com.company.model.AuthenticationRequest;
import com.company.model.AuthenticationResponse;
import com.company.model.RegistrationRequest;
import com.company.model.RegistrationResponse;
import com.company.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = {"unit-test"})
@Import(TestSecurityConfiguration.class)
class AuthenticationServiceImplTest {
    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Mock
    private UserRepository userRepository;

    @Test
    void shouldRegisterSuccessfully() {
        UUID uid = UUID.randomUUID();
        UserEntity userEntity = UserEntity.builder()
                .uid(uid)
                .name("John Doe")
                .email("john.doe@email.com")
                .password("password")
                .roles("user,admin")
                .build();
        when(userRepository.save(Mockito.any()))
                .thenReturn(userEntity);

        RegistrationResponse response = authenticationService.register(RegistrationRequest.builder().build());

        assertEquals("SUCCESS", response.getStatus());
        assertEquals("john.doe@email.com", response.getEmail());
        assertEquals(uid, response.getUid());
    }

    @Test
    void shouldLoginSuccessfully() {
        UUID uid = UUID.randomUUID();
        UserEntity johnDoe = UserEntity.builder()
                .uid(uid)
                .email("john.doe@email.com")
                .name("John Doe")
                .password("password")
                .roles("admin,user")
                .build();
        when(userRepository.findByEmailAndPassword(anyString(), anyString()))
                .thenReturn(Optional.of(johnDoe));

        AuthenticationResponse response = authenticationService.login(
                AuthenticationRequest.builder()
                        .email("john.doe@email.com")
                        .password("password")
                        .build()
        );

        assertEquals(uid, response.getUid());
        assertEquals("John Doe", response.getName());
        assertEquals("john.doe@email.com", response.getEmail());
    }

    @Test
    void shouldThrowUserNotFoundException() {
        when(userRepository.findByEmailAndPassword(anyString(), anyString()))
                .thenReturn(Optional.empty());
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            authenticationService.login(
                    AuthenticationRequest.builder()
                            .email("john.doe@email.com")
                            .password("password")
                            .build()
            );
        });

        assertEquals("Invalid email or password", exception.getMessage());
    }
}