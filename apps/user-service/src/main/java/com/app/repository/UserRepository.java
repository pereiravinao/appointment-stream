package com.app.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.app.entity.UserEntity;

@Repository
public interface UserRepository extends BaseRepository<UserEntity> {

    Optional<UserEntity> findByAuthId(String authId);

}
