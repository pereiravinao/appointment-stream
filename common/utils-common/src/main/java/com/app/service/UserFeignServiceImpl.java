package com.app.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.app.client.UserFeignClient;
import com.app.model.User;
import com.app.request.UserRegisterInternalRequest;
import feign.FeignException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class UserFeignServiceImpl {

    private final UserFeignClient userClient;

    public Long registerUser(UserRegisterInternalRequest userRegisterInternalRequest) {
        log.info("Iniciando registro de usuário: {}", userRegisterInternalRequest);
        try {
            var response = this.userClient.registerUser(userRegisterInternalRequest,
                    UserFeignClient.INTERNAL_SERVICE_VALUE);
            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Usuário registrado com sucesso: {}", response.getBody());
                return response.getBody().getId();
            }
            log.error("Falha ao registrar usuário. Status: {}", response.getStatusCode());
            return null;
        } catch (FeignException e) {
            log.error("Erro ao registrar usuário: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao registrar usuário: " + e.getMessage());
        }
    }

    public User findByAuthId(String authId) {
        log.info("Buscando usuário por authId: {}", authId);
        try {
            var response = this.userClient.findByAuthId(authId, UserFeignClient.INTERNAL_SERVICE_VALUE);
            if (response.getStatusCode().is2xxSuccessful()) {
                var user = response.getBody() != null ? response.getBody().toModel() : null;
                log.info("Usuário encontrado: {}", user);
                return user;
            }
            log.error("Falha ao buscar usuário por authId. Status: {}", response.getStatusCode());
            return null;
        } catch (FeignException e) {
            log.error("Erro ao buscar usuário por authId {}: {}", authId, e.getMessage(), e);
            throw new RuntimeException("Erro ao buscar usuário: " + e.getMessage());
        }
    }

    public Optional<User> findById(Long id) {
        log.info("Buscando usuário por id: {}", id);
        try {
            var response = this.userClient.findById(id, UserFeignClient.INTERNAL_SERVICE_VALUE);
            if (response.getStatusCode().is2xxSuccessful()) {
                var user = response.getBody() != null ? response.getBody().toModel() : null;
                log.info("Usuário encontrado: {}", user);
                return Optional.of(user);
            }
            log.error("Falha ao buscar usuário por id. Status: {}", response.getStatusCode());
        } catch (FeignException e) {
            log.error("Erro ao buscar usuário por id {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Erro ao buscar usuário: " + e.getMessage());
        }
        return Optional.empty();
    }
}
