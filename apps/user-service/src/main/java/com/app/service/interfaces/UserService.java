package com.app.service.interfaces;

import com.app.model.User;

public interface UserService {

    User getByAuthId(String authId);
}
