package com.app.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.exception.auth.AuthExceptionHandler;
import com.app.model.UserAuth;
import com.app.repository.AuthUserRepository;
import com.app.request.LoginRequest;
import com.app.service.interfaces.AuthService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthBaseServiceImpl implements AuthService {

    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenServiceImpl jwtTokenService;
    private final RefreshTokenServiceImpl refreshTokenService;

    @Override
    public UserAuth login(LoginRequest loginRequest) {
        var userAuth = this.authUserRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(AuthExceptionHandler::invalidCredentials);

        if (!this.passwordEncoder.matches(loginRequest.getPassword(), userAuth.getPassword())) {
            throw AuthExceptionHandler.invalidCredentials();
        }

        var token = this.jwtTokenService.generateToken(userAuth.toModel());
        var refreshToken = this.refreshTokenService.generateRefreshToken(userAuth.getEmail());

        var userAuthModel = userAuth.toModel();
        userAuthModel.setToken(token);
        userAuthModel.setRefreshToken(refreshToken);

        return userAuthModel;
    }

    @Override
    public void logout(String refreshToken) {
        this.refreshTokenService.deleteRefreshToken(refreshToken);
    }

}
