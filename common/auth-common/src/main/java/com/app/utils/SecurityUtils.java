package com.app.utils;

import org.springframework.security.core.context.SecurityContextHolder;

import com.app.model.UserAuth;

public class SecurityUtils {

    public static UserAuth getCurrentUser() {
        return (UserAuth) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
