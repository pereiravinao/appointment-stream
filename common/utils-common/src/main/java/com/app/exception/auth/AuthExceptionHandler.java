package com.app.exception.auth;

import org.springframework.http.HttpStatus;

public class AuthExceptionHandler extends RuntimeException {

    private HttpStatus status;

    public AuthExceptionHandler(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public static AuthExceptionHandler invalidCredentials() {
        return new AuthExceptionHandler("Email ou senha inválidos.", HttpStatus.UNAUTHORIZED);
    }

    public static AuthExceptionHandler userAlreadyExists() {
        return new AuthExceptionHandler("E-mail já cadastrado.", HttpStatus.CONFLICT);
    }

    public HttpStatus getStatus() {
        return this.status;
    }

}
