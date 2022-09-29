package com.example.shopdemo.util;

import com.example.shopdemo.exception.UnauthenticatedException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationUtilsTest {

    @Test
    void getUser() {
        assertThrows(UnauthenticatedException.class, () -> AuthenticationUtils.getUser(null));
    }
}