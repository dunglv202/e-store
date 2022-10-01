package com.example.shopdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class OrderController {
    @GetMapping("/history")
    public String showOrderHistoryPage() {
        return "orders-history";
    }
}
