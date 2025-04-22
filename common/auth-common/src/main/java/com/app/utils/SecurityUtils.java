package com.app.utils;

import org.springframework.security.core.context.SecurityContextHolder;

import com.app.enums.UserRole;
import com.app.model.UserAuth;

public class SecurityUtils {

    public static UserAuth getCurrentUserAuth() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            return null;
        }
        return (UserAuth) authentication.getPrincipal();
    }

    public static Long getOwnerId() {
        var currentUserAuth = SecurityUtils.getCurrentUserAuth();
        if (currentUserAuth == null) return null;
        var ownerId = currentUserAuth.getOwnerId();
        return ownerId != null ? ownerId : currentUserAuth.getId();
    }

    public static boolean isAdmin() {
        return getCurrentUserAuth().getRoles().contains(UserRole.ADMIN);
    }

    public static boolean hasOwnerAdmin(Long userId) {
        var user = getCurrentUserAuth();
        return user.getOwnerId() != null && user.getOwnerId().equals(userId);
    }

}
