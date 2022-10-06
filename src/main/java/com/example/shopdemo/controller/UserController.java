package com.example.shopdemo.controller;

import com.example.shopdemo.entity.User;
import com.example.shopdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

import static com.example.shopdemo.util.AuthenticationUtils.getUser;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@ModelAttribute("user") User user,
                             Authentication auth) {
        user.setAuthorities(null);
        userService.addUser(user, auth);
        return "redirect:/register?success";
    }

    @PostMapping("/account/delete")
    public String deleteAccount(Authentication auth) {
        userService.deleteUser(getUser(auth).getId(), auth);
        return "redirect:/logout";
    }
}
