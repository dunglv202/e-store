package com.example.shopdemo.controller;

import com.example.shopdemo.entity.User;
import com.example.shopdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

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
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@ModelAttribute("user") User user,
                             Model model) {
        try {
            userService.addUser(user);
        } catch (ConstraintViolationException e) {
            model.addAttribute("messages", e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet()));
            return "register";
        } catch (Exception e) {
            model.addAttribute("messages", e.getMessage());
            return "register";
        }
        return "redirect:/register?success";
    }
}
