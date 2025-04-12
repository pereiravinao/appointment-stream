package com.app.exception.auth;

public class AuthExceptionHandler extends RuntimeException {

    public AuthExceptionHandler(String message) {
        super(message);
    }

    public static AuthExceptionHandler invalidCredentials() {
        return new AuthExceptionHandler("Email ou senha inválidos.");
    }

}
