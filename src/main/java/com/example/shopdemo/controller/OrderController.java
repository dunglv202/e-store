package com.example.shopdemo.controller;

import com.example.shopdemo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.example.shopdemo.util.AuthenticationUtils.getUser;

@Controller
@RequestMapping("/order")
public class OrderController {
    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/history")
    public String showOrderHistoryPage() {
        return "order-history";
    }

    @GetMapping("/history/{orderId}")
    public String showOrderDetailsPage(@PathVariable Integer orderId,
                                       Authentication auth,
                                       Model model) {
        model.addAttribute("order", orderService.getOrder(orderId, getUser(auth)));
        return "order-details";
    }
}
