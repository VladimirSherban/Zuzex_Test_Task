package com.example.crudtest.controller;

import com.example.crudtest.model.House;
import com.example.crudtest.model.User;
import com.example.crudtest.service.HouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final HouseService houseService;

    @PostMapping("/houses/{houseId}/addTenants")
    public ResponseEntity<House> addTenantsToHouse(
            @PathVariable Long houseId,
            @RequestBody List<User> tenants,
            Authentication authentication) {
        // Получение текущего пользователя (хозяина)
        User currentUser = (User) authentication.getPrincipal();

        House updatedHouse = houseService.addTenantsToHouse(houseId, currentUser, tenants);
        if (updatedHouse == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.ok(updatedHouse);
    }
}
