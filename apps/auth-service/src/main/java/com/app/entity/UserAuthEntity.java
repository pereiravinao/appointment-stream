package com.app.entity;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.app.enums.UserRole;
import com.app.model.UserAuth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "user_auth")
@Getter
@Setter
@NoArgsConstructor
public class UserAuthEntity {
    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    private String password;

    private Set<UserRole> roles;

    public UserAuthEntity(UserAuth userAuth) {
        this.id = userAuth.getId();
        this.email = userAuth.getEmail();
        this.password = userAuth.getPassword();
        this.roles = userAuth.getRoles();
    }

    public UserAuth toModel() {
        return UserAuth.builder()
                .id(this.id)
                .email(this.email)
                .password(this.password)
                .roles(this.roles)
                .build();
    }
}
