package com.app.exception;

import org.springframework.http.HttpStatus;

public class ServicesExceptionHandler extends RuntimeException {

    private HttpStatus status;

    public ServicesExceptionHandler(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public static ServicesExceptionHandler handleException(String message) {
        return new ServicesExceptionHandler(message, HttpStatus.SERVICE_UNAVAILABLE);
    }

    public HttpStatus getStatus() {
        return this.status;
    }
}
