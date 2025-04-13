package com.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.annotation.InternalServiceOnly;
import com.app.request.UserRegisterInternalRequest;
import com.app.request.UserRequest;
import com.app.response.UserFeignResponse;
import com.app.response.UserResponse;
import com.app.service.interfaces.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{authId}")
    @InternalServiceOnly
    public ResponseEntity<UserFeignResponse> getByAuthId(@PathVariable String authId) {
        var user = this.userService.findByAuthId(authId);
        return ResponseEntity.ok(new UserFeignResponse(user));
    }

    @PostMapping("/register")
    @InternalServiceOnly
    public ResponseEntity<UserFeignResponse> register(@RequestBody UserRegisterInternalRequest request) {
        var user = this.userService.save(request.toModel());
        return ResponseEntity.ok(new UserFeignResponse(user));
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserFeignResponse> getMe() {
        var user = this.userService.findMe();
        return ResponseEntity.ok(new UserFeignResponse(user));
    }

    @PutMapping("/{id}/roles")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserResponse> updateRoles(@PathVariable Long id, @RequestBody UserRequest request) {
        var user = this.userService.update(id, request.toModel());
        return ResponseEntity.ok(new UserResponse(user));
    }
}
