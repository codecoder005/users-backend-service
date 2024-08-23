package com.company.service;

import com.company.model.request.AuthenticationRequest;
import com.company.model.response.AuthenticationResponse;
import com.company.model.request.RegistrationRequest;
import com.company.model.response.RegistrationResponse;

public interface AuthenticationService {
    RegistrationResponse register(RegistrationRequest request);

    AuthenticationResponse login(AuthenticationRequest credentials);
}
