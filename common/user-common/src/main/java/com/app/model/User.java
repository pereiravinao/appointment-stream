package com.app.model;

import com.app.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class User {
    private String id;
    private String username;
    private String email;
    private String password;
    private Set<UserRole> roles;
    private String status;
    private String createdAt;
}
