package com.app.entity;

import java.util.Set;

import com.app.enums.UserRole;
import com.app.model.User;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "tb_user")
@Getter
@Setter
public class UserEntity extends BaseEntity {

    private String name;
    private String authId;
    private String email;
    private String phone;

    @ElementCollection(targetClass = UserRole.class)
    @CollectionTable(name = "tb_user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyColumn(name = "role")
    @Column(name = "role")
    private Set<UserRole> roles;

    public User toModel() {
        return User.builder()
                .id(super.getId())
                .authId(this.authId)
                .name(this.name)
                .email(this.email)
                .phone(this.phone)
                .roles(this.roles)
                .createdAt(super.getCreatedAt())
                .updatedAt(super.getUpdatedAt())
                .build();
    }

    public UserEntity(User user) {
        super.setId(user.getId());
        super.setCreatedAt(user.getCreatedAt());
        super.setUpdatedAt(user.getUpdatedAt());
        this.authId = user.getAuthId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.roles = user.getRoles();
    }
}
