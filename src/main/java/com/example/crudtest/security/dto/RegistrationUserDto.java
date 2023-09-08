package com.example.crudtest.security.dto;

import com.example.crudtest.security.utils.Role;
import lombok.Data;

@Data
public class RegistrationUserDto {
    private String name;
    private String password;
    private Role role;
}
