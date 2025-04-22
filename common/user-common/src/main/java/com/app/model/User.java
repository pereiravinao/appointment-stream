package com.app.model;

import java.time.LocalDateTime;
import java.util.Set;

import com.app.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String authId;
    private String name;
    private String email;
    private String phone;
    private Set<UserRole> roles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long ownerId;

    public User(Long id) {
        this.id = id;
    }
}
