package com.app.exception.auth;

public class TokenException extends RuntimeException {

    public TokenException(String message) {
        super(message);
    }

    public static TokenException expired() {
        return new TokenException("Token expirado");
    }

    public static TokenException invalid() {
        return new TokenException("Token inválido");
    }

    public static TokenException missing() {
        return new TokenException("Token não fornecido");
    }

    public static TokenException malformed() {
        return new TokenException("Token com formato inválido");
    }
}