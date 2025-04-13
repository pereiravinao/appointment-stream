package com.app.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationError {
    
    private List<String> errors = new ArrayList<>();
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();
    
    private String path;
    
    public void addError(String error) {
        this.errors.add(error);
    }
} 