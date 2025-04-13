package com.app.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        logger.error("Runtime error occurred: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(),
                        ex.getStackTrace().toString()));
    }

    @ExceptionHandler(RefreshTokenException.class)
    public ResponseEntity<Map<String, Object>> handleRefreshTokenException(RefreshTokenException ex) {
        logger.error("Refresh token error: ", ex);
        return ResponseEntity.status(ex.getStatus())
                .body(getErrorResponse(ex.getStatus(), ex.getMessage(), ex.getStackTrace().toString()));
    }

    @ExceptionHandler(UserExceptionHandler.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFoundException(UserExceptionHandler ex) {
        logger.error("User error: ", ex);
        return ResponseEntity.status(ex.getStatus())
                .body(getErrorResponse(ex.getStatus(), ex.getMessage(), ex.getStackTrace().toString()));
    }

    @ExceptionHandler(AuthExceptionHandler.class)
    public ResponseEntity<Map<String, Object>> handleAuthException(AuthExceptionHandler ex) {
        logger.error("Authentication error: ", ex);
        return ResponseEntity.status(ex.getStatus())
                .body(getErrorResponse(ex.getStatus(), ex.getMessage(), ex.getStackTrace().toString()));
    }

    @ExceptionHandler(ServicesExceptionHandler.class)
    public ResponseEntity<Map<String, Object>> handleServicesException(ServicesExceptionHandler ex) {
        logger.error("Services error: ", ex);
        return ResponseEntity.status(ex.getStatus())
                .body(getErrorResponse(ex.getStatus(), ex.getMessage(), ex.getStackTrace().toString()));
    }

    private Map<String, Object> getErrorResponse(HttpStatus status, String message, String stackTrace) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", status.value());
        errorResponse.put("error", status.getReasonPhrase());
        errorResponse.put("message", message);

        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        StringBuilder detailedStackTrace = new StringBuilder();
        detailedStackTrace.append("Local do erro:\n");

        for (StackTraceElement element : stackTraceElements) {
            if (element.getClassName().startsWith("com.app")) {
                // Formata cada linha do stack trace de forma mais amigável
                String className = element.getClassName().substring(element.getClassName().lastIndexOf(".") + 1);
                String methodName = element.getMethodName();
                String fileName = element.getFileName();
                int lineNumber = element.getLineNumber();

                detailedStackTrace.append("  → ")
                        .append(className)
                        .append(".")
                        .append(methodName)
                        .append(" (")
                        .append(fileName)
                        .append(":")
                        .append(lineNumber)
                        .append(")\n");
            }
        }
        errorResponse.put("stackTrace", detailedStackTrace.toString());

        errorResponse.put("timestamp", LocalDateTime.now().toString());
        return errorResponse;
    }

}
