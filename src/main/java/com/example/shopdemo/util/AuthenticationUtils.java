package com.example.shopdemo.util;

import com.example.shopdemo.entity.User;
import com.example.shopdemo.exception.UnauthenticatedException;
import com.example.shopdemo.security.CustomUserDetails;
import org.springframework.security.core.Authentication;

public class AuthenticationUtils {
    public static User getUser(Authentication auth) {
        if (auth == null || !auth.isAuthenticated())
            throw new UnauthenticatedException("Unauthenticated");
        return ((CustomUserDetails) auth.getPrincipal()).getUser();
    }
}
