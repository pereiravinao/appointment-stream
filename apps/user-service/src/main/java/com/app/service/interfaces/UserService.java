package com.app.service.interfaces;

import org.springframework.data.domain.Page;

import com.app.criteria.UserCriteria;
import com.app.model.User;

public interface UserService {

    User findByAuthId(String authId);

    User save(User user);

    User findMe();

    User update(Long id, User user);

    Page<User> findAll(UserCriteria criteria);
}
