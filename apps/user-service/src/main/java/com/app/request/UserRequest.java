package com.app.request;

import java.util.Set;

import com.app.enums.UserRole;
import com.app.model.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequest {

    private String name;
    private String email;
    private String phone;
    private Set<UserRole> roles;

    public User toModel() {
        return User.builder()
                .name(this.name)
                .email(this.email)
                .phone(this.phone)
                .roles(this.roles)
                .build();
    }
}
