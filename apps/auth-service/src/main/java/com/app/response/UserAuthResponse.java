package com.app.response;

import java.util.Set;

import com.app.enums.UserRole;
import com.app.model.UserAuth;

import lombok.Getter;

@Getter
public class UserAuthResponse {

    private String authId;
    private Long id;
    private String email;
    private Set<UserRole> roles;

    public UserAuthResponse(UserAuth userAuth) {
        this.authId = userAuth.getAuthId();
        this.id = userAuth.getId();
        this.email = userAuth.getEmail();
        this.roles = userAuth.getRoles();
    }
}
