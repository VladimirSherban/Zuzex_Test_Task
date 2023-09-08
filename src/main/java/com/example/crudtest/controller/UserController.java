package com.example.crudtest.controller;

import com.example.crudtest.model.User;
import com.example.crudtest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/secured")
public class UserController {

    private final UserService userService;

    @GetMapping("/info")
    public String userData(Principal principal) {
        return principal.getName();
    }

    @GetMapping("/showUser")
    public ResponseEntity<Optional<User>> showUserByName(String name) {
        Optional<User> userByName = userService.getUserByName(name);
        if (userByName.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userByName);
        }
        return ResponseEntity.ok(userByName);
    }

    @PutMapping("/updateUser/{userName}")
    public ResponseEntity<User> updateUser(@PathVariable String userName, @RequestBody User updatedUserData) {
        User updatedUser = userService.updateUser(userName, updatedUserData);

        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/deleteUser/{userName}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userName) {
        boolean deleted = userService.deleteUser(userName);

        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
