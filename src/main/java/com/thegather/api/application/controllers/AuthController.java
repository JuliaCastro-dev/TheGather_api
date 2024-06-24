package com.thegather.api.application.controllers;

import com.google.gson.Gson;
import com.thegather.api.domain.entities.LoginCredentials;
import com.thegather.api.domain.entities.User;
import com.thegather.api.domain.interfaces.services.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final IUserService userService;

    public AuthController(IUserService userService) {
        this.userService = userService;
    }

    Gson gson = new Gson();

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginCredentials credentials) {
        User user = userService.getUserByEmail(credentials.getEmail());
        if (user != null && user.getPassword().equals(credentials.getPassword())) {
            return ResponseEntity.ok(gson.toJson(user));
        } else {
            return ResponseEntity.badRequest().body("Invalid username or password");
        }
    }

}
