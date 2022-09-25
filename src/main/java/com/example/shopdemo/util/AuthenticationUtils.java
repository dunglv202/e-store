package com.example.shopdemo.util;

import com.example.shopdemo.entity.User;
import com.example.shopdemo.security.CustomUserDetails;
import org.springframework.security.core.Authentication;

public class AuthenticationUtils {
    public static User getUser(Authentication auth) {
        return ((CustomUserDetails) auth.getPrincipal()).getUser();
    }
}
