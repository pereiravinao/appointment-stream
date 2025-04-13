package com.app.service.impl;

import org.springframework.stereotype.Service;

import com.app.model.User;
import com.app.service.interfaces.UserService;

@Service
public class UserBaseServiceImpl implements UserService {

    @Override
    public User getByAuthId(String authId) {
        return null;
    }
}
