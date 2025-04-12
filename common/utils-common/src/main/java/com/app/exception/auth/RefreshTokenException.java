package com.app.exception.auth;

public class RefreshTokenException extends RuntimeException {

    public RefreshTokenException(String message) {
        super(message);
    }

    public static RefreshTokenException expired() {
        return new RefreshTokenException("Refresh token expirado");
    }

    public static RefreshTokenException invalid() {
        return new RefreshTokenException("Refresh token inválido");
    }

}