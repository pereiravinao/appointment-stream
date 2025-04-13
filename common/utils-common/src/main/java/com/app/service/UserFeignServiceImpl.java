package com.app.service;

import org.springframework.stereotype.Service;

import com.app.client.UserClient;
import com.app.request.UserRegisterInternalRequest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class UserFeignServiceImpl {

    private final UserClient userClient;

    public void registerUser(UserRegisterInternalRequest userRegisterInternalRequest) {
        var response = this.userClient.registerUser(userRegisterInternalRequest, UserClient.INTERNAL_SERVICE_VALUE);
        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Usuário registrado com sucesso: {}", response.getBody());
        }
    }
}
