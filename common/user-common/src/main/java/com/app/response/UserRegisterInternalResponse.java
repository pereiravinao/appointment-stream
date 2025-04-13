package com.app.response;

import java.util.Set;

import com.app.enums.UserRole;
import com.app.model.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegisterInternalResponse {

    private String authId;
    private String name;
    private String email;
    private Set<UserRole> roles;

    public static UserRegisterInternalResponse fromModel(User user) {
        return UserRegisterInternalResponse.builder()
                .authId(user.getAuthId())
                .name(user.getName())
                .email(user.getEmail())
                .roles(user.getRoles())
                .build();
    }
}