package com.app.response;

import java.util.Set;

import com.app.enums.UserRole;
import com.app.model.User;

import lombok.Data;

@Data
public class UserFeignResponse {

    private Long id;
    private String authId;
    private String name;
    private String email;
    private String phone;
    private Set<UserRole> roles;

    public UserFeignResponse(User user) {
        this.id = user.getId();
        this.authId = user.getAuthId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.roles = user.getRoles();
    }

    public User toModel() {
        return User.builder()
                .id(this.id)
                .authId(this.authId)
                .name(this.name)
                .email(this.email)
                .phone(this.phone)
                .roles(this.roles)
                .build();
    }
}
