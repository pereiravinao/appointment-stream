package com.app.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.app.enums.UserRole;
import com.app.model.User;
import com.app.model.UserAuth;
import com.app.service.UserFeignServiceImpl;

public class SecurityUtils {

    private static UserFeignServiceImpl userFeignService;

    @Component
    public static class SecurityUtilsService {
        public SecurityUtilsService(UserFeignServiceImpl userFeignService) {
            SecurityUtils.userFeignService = userFeignService;
        }
    }

    public static UserAuth getCurrentUserAuth() {
        return (UserAuth) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static boolean isAdmin() {
        return getCurrentUserAuth().getRoles().contains(UserRole.ADMIN);
    }

    public static User getCurrentUser() {
        return findUserByAuthId(getCurrentUserAuth().getAuthId());
    }

    public static boolean hasOwnerAdmin(Long userId) {
        User user = findUserByAuthId(getCurrentUserAuth().getAuthId());
        return user.getOwnerId() != null && user.getOwnerId().equals(userId);
    }

    private static User findUserByAuthId(String authId) {
        return userFeignService.findByAuthId(authId);
    }
}
