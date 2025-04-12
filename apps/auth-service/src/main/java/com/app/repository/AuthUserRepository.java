package com.app.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.app.entity.UserAuthEntity;

public interface AuthUserRepository extends MongoRepository<UserAuthEntity, String> {
    Optional<UserAuthEntity> findByEmail(String email);
}
