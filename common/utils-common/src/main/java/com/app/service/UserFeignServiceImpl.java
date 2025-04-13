package com.app.service;

import org.springframework.stereotype.Service;

import com.app.client.UserFeignClient;
import com.app.model.User;
import com.app.request.UserRegisterInternalRequest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class UserFeignServiceImpl {

    private final UserFeignClient userClient;

    public void registerUser(UserRegisterInternalRequest userRegisterInternalRequest) {
        var response = this.userClient.registerUser(userRegisterInternalRequest,
                UserFeignClient.INTERNAL_SERVICE_VALUE);
        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Usuário registrado com sucesso: {}", response.getBody());
        }
    }

    public User findByAuthId(String authId) {
        var response = this.userClient.findByAuthId(authId, UserFeignClient.INTERNAL_SERVICE_VALUE);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody() != null ? response.getBody().toModel() : null;
        }
        return null;
    }
}
