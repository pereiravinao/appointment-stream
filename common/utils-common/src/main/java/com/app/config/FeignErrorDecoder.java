package com.app.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() >= 400 && response.status() <= 499) {
            log.error("Client error occurred while calling {}: {}", methodKey, response.reason());
            return new ResponseStatusException(HttpStatus.valueOf(response.status()), response.reason());
        }
        if (response.status() >= 500 && response.status() <= 599) {
            log.error("Server error occurred while calling {}: {}", methodKey, response.reason());
            return new ResponseStatusException(HttpStatus.valueOf(response.status()), response.reason());
        }
        return defaultErrorDecoder.decode(methodKey, response);
    }
}