package com.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.request.LoginRequest;
import com.app.request.RegisterRequest;
import com.app.response.UserAuthResponse;
import com.app.service.interfaces.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private static final String REFRESH_TOKEN_NAME = "rtkn";
    private static final String ACCESS_TOKEN_NAME = "tkn";
    private static final int COOKIE_EXPIRATION = 60 * 60 * 24 * 7;

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<UserAuthResponse> login(@Valid @RequestBody LoginRequest loginRequest,
            HttpServletResponse response) {
        var userAuth = this.authService.login(loginRequest);
        this.buildCookie(response, ACCESS_TOKEN_NAME, userAuth.getToken());
        this.buildCookie(response, REFRESH_TOKEN_NAME, userAuth.getRefreshToken());
        return ResponseEntity.ok(new UserAuthResponse(userAuth));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        var refreshToken = this.getRefreshToken(request);
        this.authService.logout(refreshToken);
        this.buildCookie(response, ACCESS_TOKEN_NAME, null);
        this.buildCookie(response, REFRESH_TOKEN_NAME, null);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/register")
    public ResponseEntity<UserAuthResponse> register(@Valid @RequestBody RegisterRequest registerRequest,
            HttpServletResponse response) {
        var userAuth = this.authService.register(registerRequest);
        this.buildCookie(response, ACCESS_TOKEN_NAME, userAuth.getToken());
        this.buildCookie(response, REFRESH_TOKEN_NAME, userAuth.getRefreshToken());
        return new ResponseEntity<>(new UserAuthResponse(userAuth), HttpStatus.CREATED);
    }

    private void buildCookie(HttpServletResponse response, String tokenName, String token) {
        Cookie cookie = new Cookie(tokenName, token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(COOKIE_EXPIRATION);
        response.addCookie(cookie);
    }

    private String getRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(REFRESH_TOKEN_NAME)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
