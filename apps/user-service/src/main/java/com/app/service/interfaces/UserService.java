package com.app.service.interfaces;

import com.app.model.User;

public interface UserService {

    User findByAuthId(String authId);

    User save(User user);

    User findMe();

    User update(Long id, User user);
}
