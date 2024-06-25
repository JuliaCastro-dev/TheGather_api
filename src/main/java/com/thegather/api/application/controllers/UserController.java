package com.thegather.api.application.controllers;

import com.google.gson.Gson;
import com.thegather.api.domain.entities.LoginCredentials;
import com.thegather.api.domain.entities.User;
import com.thegather.api.domain.interfaces.services.IUserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController("users")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    Gson gson = new Gson();
    @GetMapping(value = "/users")
    public ResponseEntity<String> getAllUsers() {
        List<User> users = userService.getAllUsers();
        String jsonString = gson.toJson(users);
        return ResponseEntity.ok(jsonString);
    }

    @PostMapping(value = "/newUser")
    public ResponseEntity<String> createUser(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Error when trying to create user, invalid user:  ");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(error.getDefaultMessage()).append(" ");
            }
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }

        User savedUser = userService.createUser(user);
        String jsonString = gson.toJson(savedUser);
        return ResponseEntity.ok(jsonString);
    }

    @PutMapping(value = "/user/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Error when trying to update user:  ");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(error.getDefaultMessage()).append(" ");
            }
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }

        user.setId(id);

        int updateUser = userService.updateUser(user);

        if (updateUser == 0 ) return ResponseEntity.notFound().build();

        User userUpdated = userService.getUserById(id);
        String jsonString = gson.toJson(userUpdated);
        return ResponseEntity.ok(jsonString);
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<String> getEventById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        String jsonString = gson.toJson(user);
        return ResponseEntity.ok(jsonString);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Long id) {
        boolean ret = userService.deleteUser(id);
        if(!ret) {
            return ResponseEntity.badRequest().body(false);
        }
        else {
            return ResponseEntity.ok(true);
        }
    }

}
