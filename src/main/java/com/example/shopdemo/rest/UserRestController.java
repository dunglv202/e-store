package com.example.shopdemo.rest;

import com.example.shopdemo.entity.Authority;
import com.example.shopdemo.entity.User;
import com.example.shopdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {
    private UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public User register(@RequestBody User user) {
        return userService.addUser(user);
    }
}
