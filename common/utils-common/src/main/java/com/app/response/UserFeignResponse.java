package com.app.response;

import java.util.Set;

import com.app.enums.UserRole;
import com.app.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserFeignResponse {

    private Long id;
    private String authId;
    private String name;
    private String email;
    private String phone;
    private Set<UserRole> roles;
    private Long ownerId;

    public UserFeignResponse(User user) {
        this.id = user.getId();
        this.authId = user.getAuthId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.roles = user.getRoles();
        this.ownerId = user.getOwnerId();
    }

    public User toModel() {
        return User.builder()
                .id(this.id)
                .authId(this.authId)
                .name(this.name)
                .email(this.email)
                .phone(this.phone)
                .roles(this.roles)
                .ownerId(this.ownerId)
                .build();
    }
}
