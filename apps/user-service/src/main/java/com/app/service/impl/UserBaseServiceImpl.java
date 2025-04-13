package com.app.service.impl;

import org.springframework.stereotype.Service;

import com.app.entity.UserEntity;
import com.app.exception.user.UserExceptionHandler;
import com.app.model.User;
import com.app.repository.UserRepository;
import com.app.service.interfaces.UserService;
import com.app.utils.SecurityUtils;

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
        var userEntity = this.userRepository.findByAuthId(user.getAuthId());
        if (userEntity.isPresent()) {
            userEntity.get().update(user);
            return this.userRepository.save(userEntity.get()).toModel();
        }
        return this.userRepository.save(new UserEntity(user)).toModel();
    }

    @Override
    public User findMe() {
        var userAuth = SecurityUtils.getCurrentUser();
        var user = this.userRepository.findByAuthId(userAuth.getId()).orElseThrow(UserExceptionHandler::notFound);
        return user.toModel();
    }

    @Override
    public User update(Long id, User user) {
        var userEntity = this.userRepository.findById(id).orElseThrow(UserExceptionHandler::notFound);
        this.validateOwnerAdmin(userEntity.toModel());
        userEntity.update(user);
        return this.userRepository.save(userEntity).toModel();
    }

    private void validateOwnerAdmin(User user) {
        if (!SecurityUtils.hasOwnerAdmin(user.getId())) {
            throw UserExceptionHandler.notFound();
        }
    }

}
