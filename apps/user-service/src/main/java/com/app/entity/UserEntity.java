package com.app.entity;

import java.util.Set;

import com.app.enums.UserRole;

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

}
