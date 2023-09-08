package com.example.crudtest.service;

import com.example.crudtest.security.dto.auth.AuthenticationRequest;
import com.example.crudtest.security.dto.auth.AuthenticationResponse;
import com.example.crudtest.security.dto.auth.RegisterRequest;

public interface AuthenticationService {

    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
