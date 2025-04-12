package com.app.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "refresh_token")
@Getter
@Setter
public class RefreshTokenEntity {
    @Id
    private String id;

    private String token;

    private String userId;

    private LocalDateTime expiresAt;

}
