package com.app.entity;

import java.util.Set;

import com.app.enums.UserRole;
import com.app.model.User;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "tb_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaseEntity {

    private String name;

    @Column(name = "auth_id", unique = true)
    private String authId;

    @Column(name = "email", unique = true)
    private String email;

    private String phone;

    @ElementCollection(targetClass = UserRole.class)
    @CollectionTable(name = "tb_user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyColumn(name = "role")
    @Column(name = "role")
    private Set<UserRole> roles;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UserEntity ownerAdmin;

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
                .ownerAdmin(this.ownerAdmin != null ? this.ownerAdmin.toModel() : null)
                .build();
    }

    public void update(User user) {
        this.name = user.getName() != null ? user.getName() : this.name;
        this.email = user.getEmail() != null ? user.getEmail() : this.email;
        this.phone = user.getPhone() != null ? user.getPhone() : this.phone;
        this.roles.addAll(user.getRoles());
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
        this.ownerAdmin = user.getOwnerAdmin() != null ? new UserEntity(user.getOwnerAdmin()) : null;
    }
}
