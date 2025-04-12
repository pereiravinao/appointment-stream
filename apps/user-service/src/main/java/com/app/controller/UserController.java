package com.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.User;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @GetMapping
    public ResponseEntity<User> getUser() {
        return ResponseEntity.ok(new User());
    }
}
