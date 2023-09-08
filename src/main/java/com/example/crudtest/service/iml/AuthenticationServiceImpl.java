package com.example.crudtest.service.iml;

import com.example.crudtest.model.User;
import com.example.crudtest.repository.UserDao;
import com.example.crudtest.security.config.JwtService;
import com.example.crudtest.security.dto.auth.AuthenticationRequest;
import com.example.crudtest.security.dto.auth.AuthenticationResponse;
import com.example.crudtest.security.dto.auth.RegisterRequest;
import com.example.crudtest.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserDao repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        Optional<User> byName = repository.findByNameAndDeletedStatusFalse(request.getName());
        String nameNewUser = request.getName();
        if (byName.isPresent()) {
            if (nameNewUser.equals(byName.get().getName())) {
                log.warn("createUser: Пользователь с именем {} уже существует", nameNewUser);
                return null;
            }
        }

        User user = User.builder()
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .age(request.getAge())
                .deletedStatus(false)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getName(),
                        request.getPassword()
                )
        );
        var user = repository.findByNameAndDeletedStatusFalse(request.getName())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }
}
