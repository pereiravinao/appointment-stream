package com.app.service.impl;

import org.springframework.stereotype.Service;

import com.app.entity.UserEntity;
import com.app.exception.user.UserExceptionHandler;
import com.app.model.User;
import com.app.repository.UserRepository;
import com.app.service.interfaces.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserBaseServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findByAuthId(String authId) {
        return this.userRepository.findByAuthId(authId)
                .map(UserEntity::toModel)
                .orElseThrow(UserExceptionHandler::notFound);
    }

    @Override
    public User save(User user) {
        return this.userRepository.save(new UserEntity(user)).toModel();
    }
}
