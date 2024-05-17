package com.company.service;

import com.company.model.AuthenticationRequest;
import com.company.model.AuthenticationResponse;
import com.company.model.RegistrationRequest;
import com.company.model.RegistrationResponse;

public interface AuthenticationService {
    RegistrationResponse register(RegistrationRequest request);

    AuthenticationResponse login(AuthenticationRequest credentials);
}
