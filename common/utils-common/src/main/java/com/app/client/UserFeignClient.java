package com.app.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.app.config.FeignConfig;
import com.app.request.UserRegisterInternalRequest;
import com.app.response.UserFeignResponse;
import com.app.response.UserRegisterInternalResponse;

@FeignClient(name = "http://localhost:8082", configuration = FeignConfig.class)
public interface UserFeignClient {

    String USER_SERVICE_PATH = "/api/v1/users";
    String HEADER_INTERNAL_SERVICE = "X-Internal-Service";
    static final String INTERNAL_SERVICE_VALUE = "user-service";

    @PostMapping(USER_SERVICE_PATH + "/register")
    ResponseEntity<UserRegisterInternalResponse> registerUser(@RequestBody UserRegisterInternalRequest request,
            @RequestHeader(HEADER_INTERNAL_SERVICE) String internalService);

    @GetMapping(USER_SERVICE_PATH + "/{authId}")
    ResponseEntity<UserFeignResponse> findByAuthId(@PathVariable String authId,
            @RequestHeader(HEADER_INTERNAL_SERVICE) String internalService);
}