package com.app.exception.user;

import org.springframework.http.HttpStatus;

public class UserExceptionHandler extends RuntimeException {

    private HttpStatus status;

    public UserExceptionHandler(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public static UserExceptionHandler notFound() {
        return new UserExceptionHandler("Usuário não encontrado", HttpStatus.NOT_FOUND);
    }

    public static UserExceptionHandler invalidPassword() {
        return new UserExceptionHandler("Senha inválida", HttpStatus.UNAUTHORIZED);
    }

    public HttpStatus getStatus() {
        return this.status;
    }

}
