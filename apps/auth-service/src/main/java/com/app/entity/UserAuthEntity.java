package com.app.entity;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.app.enums.UserRole;
import com.app.model.UserAuth;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "user_auth")
@Getter
@Setter
public class UserAuthEntity {
    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    private String password;

    private Set<UserRole> roles;

    private String status;

    public UserAuth toModel() {
        return UserAuth.builder()
                .id(this.id)
                .email(this.email)
                .roles(this.roles)
                .status(this.status)
                .build();
    }
}
