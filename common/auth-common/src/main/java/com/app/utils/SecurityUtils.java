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

    public static UserAuth getCurrentUser() {
        return (UserAuth) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static boolean isAdmin() {
        return getCurrentUser().getRoles().contains(UserRole.ADMIN);
    }

    public static boolean hasOwnerAdmin(Long userId) {
        User user = findUserByAuthId(getCurrentUser().getId());
        return user.getOwnerAdmin() != null && user.getOwnerAdmin().getId().equals(userId);
    }

    private static User findUserByAuthId(String authId) {
        return userFeignService.findByAuthId(authId);
    }
}
