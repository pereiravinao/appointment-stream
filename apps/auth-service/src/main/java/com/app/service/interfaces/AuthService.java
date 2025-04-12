package com.app.service.interfaces;

import com.app.model.UserAuth;
import com.app.request.LoginRequest;

public interface AuthService {
    UserAuth login(LoginRequest loginRequest);

    void logout(String refreshToken);
}
