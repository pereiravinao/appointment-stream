package com.app.exception.user;

public class UserExceptionHandler extends RuntimeException {

    public UserExceptionHandler(String message) {
        super(message);
    }

    public static UserExceptionHandler notFound() {
        return new UserExceptionHandler("Usuário não encontrado");
    }

    public static UserExceptionHandler invalidPassword() {
        return new UserExceptionHandler("Senha inválida");
    }

}
