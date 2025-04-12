package com.app.service.interfaces;

import com.app.model.UserAuth;
import com.app.request.LoginRequest;
import com.app.request.RegisterRequest;

public interface AuthService {
    UserAuth login(LoginRequest loginRequest);

    void logout(String refreshToken);

    UserAuth register(RegisterRequest registerRequest);
}
