package com.app.model;

import java.util.Set;

import com.app.enums.UserRole;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserAuth {
    private String id;
    private String name;
    private String email;
    private String password;
    private Set<UserRole> roles;
    private String token;
    private String refreshToken;
}
