package com.example.crudtest.controller;

import com.example.crudtest.model.User;
import com.example.crudtest.security.dto.auth.AuthenticationRequest;
import com.example.crudtest.security.dto.auth.AuthenticationResponse;
import com.example.crudtest.security.dto.auth.RegisterRequest;
import com.example.crudtest.service.AuthenticationService;
import com.example.crudtest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (request == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Пользователь с таким именем уже существует.");
        }
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        User createUser = userService.createUser(user);
        if (createUser == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Пользователь с таким именем уже существует.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createUser);
    }
}
