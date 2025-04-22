package com.app.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

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
        try {
            String responseBody = new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
            log.error("Erro na chamada do método {}: Status: {}, Reason: {}, Body: {}",
                    methodKey,
                    response.status(),
                    response.reason(),
                    responseBody);
        } catch (IOException e) {
            log.error("Erro ao ler o corpo da resposta", e);
        }

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