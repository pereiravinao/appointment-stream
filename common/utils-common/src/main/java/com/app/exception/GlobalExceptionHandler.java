package com.app.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.app.exception.auth.AuthExceptionHandler;
import com.app.exception.auth.RefreshTokenException;
import com.app.exception.user.UserExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(),
                        ex.getStackTrace().toString()));
    }

    @ExceptionHandler(RefreshTokenException.class)
    public ResponseEntity<Map<String, Object>> handleRefreshTokenException(RefreshTokenException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(getErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), ex.getStackTrace().toString()));
    }

    @ExceptionHandler(UserExceptionHandler.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFoundException(UserExceptionHandler ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(getErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), ex.getStackTrace().toString()));
    }

    @ExceptionHandler(AuthExceptionHandler.class)
    public ResponseEntity<Map<String, Object>> handleAuthException(AuthExceptionHandler ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(getErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), ex.getStackTrace().toString()));
    }

    private Map<String, Object> getErrorResponse(HttpStatus status, String message, String stackTrace) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", status.value());
        errorResponse.put("error", status.getReasonPhrase());
        errorResponse.put("message", message);
        errorResponse.put("stackTrace", stackTrace);
        errorResponse.put("timestamp", LocalDateTime.now().toString());
        return errorResponse;
    }

}
