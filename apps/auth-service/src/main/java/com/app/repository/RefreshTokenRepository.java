package com.app.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.app.entity.RefreshTokenEntity;

public interface RefreshTokenRepository extends MongoRepository<RefreshTokenEntity, String> {
    Optional<RefreshTokenEntity> findByToken(String token);

    void deleteByToken(String token);

    void deleteByUserId(String userId);
}
