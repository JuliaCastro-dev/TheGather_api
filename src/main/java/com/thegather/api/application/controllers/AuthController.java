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

@RestController("auth")
public class AuthController {
    private final IUserService userService;

    public AuthController(IUserService userService) {
        this.userService = userService;
    }

    Gson gson = new Gson();

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody LoginCredentials credentials) {
        User user = userService.getUserByEmail(credentials.email);

        if (user == null) {
            System.out.println("User not found for email: " + credentials.email);
            return ResponseEntity.badRequest().body("Invalid username or password");
        }

        if (user != null && user.getPassword().equals(credentials.password)) {
            System.out.println("Achou usuário para login");
            return ResponseEntity.ok(gson.toJson(user));
        } else {
            System.out.println("Senha errada");
            return ResponseEntity.badRequest().body("Invalid username or password");
        }
    }

}
