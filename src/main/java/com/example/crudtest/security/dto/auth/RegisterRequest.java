package com.example.crudtest.security.dto.auth;

import com.example.crudtest.security.utils.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String name;
    private String password;
    private Integer age;
    private Role role;
}

