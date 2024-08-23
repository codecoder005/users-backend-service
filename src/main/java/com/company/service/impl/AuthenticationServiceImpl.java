package com.company.service.impl;

import com.company.entity.UserEntity;
import com.company.exception.UserNotFoundException;
import com.company.model.request.AuthenticationRequest;
import com.company.model.response.AuthenticationResponse;
import com.company.model.request.RegistrationRequest;
import com.company.model.response.RegistrationResponse;
import com.company.repository.UserRepository;
import com.company.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;

    @Override
    public RegistrationResponse register(RegistrationRequest request) {
        UserEntity newUser = UserEntity.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .roles(request.getRoles())
                .build();
        UserEntity createdUser = userRepository.save(newUser);
        return RegistrationResponse.builder()
                .status("SUCCESS")
                .uid(createdUser.getUid())
                .email(createdUser.getEmail())
                .registeredOn(LocalDateTime.now())
                .build();
    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest credentials) {
        var optional = userRepository.findByEmailAndPassword(credentials.getEmail(), credentials.getPassword());
        if (optional.isPresent()) {
            return AuthenticationResponse.builder()
                    .uid(optional.get().getUid())
                    .email(optional.get().getEmail())
                    .name(optional.get().getName())
                    .roles(Arrays.stream(optional.get().getRoles().split(",")).toList())
                    .build();
        }
        throw new UserNotFoundException("Invalid email or password");
    }
}
