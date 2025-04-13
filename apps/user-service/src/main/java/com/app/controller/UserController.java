package com.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.User;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @GetMapping("/me/{authId}")
    public ResponseEntity<User> getByAuthId(@PathVariable String authId) {
        return ResponseEntity.ok(new User());
    }
}
