package com.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.annotation.InternalServiceOnly;
import com.app.model.User;
import com.app.request.UserRegisterInternalRequest;
import com.app.service.interfaces.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{authId}")
    @InternalServiceOnly
    public ResponseEntity<User> getByAuthId(@PathVariable String authId) {
        return ResponseEntity.ok(this.userService.findByAuthId(authId));
    }

    @PostMapping("/register")
    @InternalServiceOnly
    public ResponseEntity<User> register(@RequestBody UserRegisterInternalRequest request) {
        return ResponseEntity.ok(this.userService.save(request.toModel()));
    }
}
