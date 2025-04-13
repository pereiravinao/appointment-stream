package com.app.response;

import java.util.Set;

import com.app.enums.UserRole;
import com.app.model.UserAuth;

import lombok.Getter;

@Getter
public class UserAuthResponse {

    private String id;
    private String email;
    private Set<UserRole> roles;

    public UserAuthResponse(UserAuth userAuth) {
        this.id = userAuth.getId();
        this.email = userAuth.getEmail();
        this.roles = userAuth.getRoles();
    }
}
