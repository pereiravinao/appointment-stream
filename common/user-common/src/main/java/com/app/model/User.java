package com.app.model;

import java.time.LocalDateTime;
import java.util.Set;

import com.app.enums.UserRole;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class User {
    private Long id;
    private String authId;
    private String name;
    private String email;
    private String phone;
    private Set<UserRole> roles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private User ownerAdmin;
}
