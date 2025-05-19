package com.app.request;

import java.util.Set;

import com.app.enums.UserRole;
import com.app.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterInternalRequest {

    private String authId;
    private String name;
    private String email;
    private Set<UserRole> roles;
    private Long ownerId;

    public User toModel() {
        return User.builder()
                .authId(this.authId)
                .name(this.name)
                .email(this.email)
                .roles(this.roles)
                .ownerId(this.ownerId)
                .build();
    }
}
