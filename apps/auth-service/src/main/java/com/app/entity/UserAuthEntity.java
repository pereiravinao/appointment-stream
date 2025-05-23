package com.app.entity;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

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
    private String authId;
    
    private Long id;

    private Long ownerId;

    @Indexed(unique = true)
    private String email;

    private String name;

    private String password;

    @Field(targetType = FieldType.STRING)
    private Set<UserRole> roles;

    public UserAuthEntity(UserAuth userAuth) {
        this.id = userAuth.getId();
        this.authId = userAuth.getAuthId();
        this.email = userAuth.getEmail();
        this.name = userAuth.getName();
        this.password = userAuth.getPassword();
        this.roles = userAuth.getRoles();
        this.ownerId = userAuth.getOwnerId();
    }

    public UserAuth toModel() {
        return UserAuth.builder()
                .id(this.id)
                .authId(this.authId)
                .ownerId(this.ownerId)
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .roles(this.roles)
                .build();
    }
}
