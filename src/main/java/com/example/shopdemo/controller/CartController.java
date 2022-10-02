package com.example.shopdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
public class CartController {
    @GetMapping("")
    public String showCart() {
        return "cart";
    }

    @GetMapping("/checkout")
    public String showCheckoutPage() {
        return "checkout";
    }
}
