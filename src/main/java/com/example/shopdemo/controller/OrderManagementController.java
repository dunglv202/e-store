package com.example.shopdemo.controller;

import com.example.shopdemo.entity.Order;
import com.example.shopdemo.enumtype.OrderOrderingStrategy;
import com.example.shopdemo.enumtype.OrderStatus;
import com.example.shopdemo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.example.shopdemo.util.AuthenticationUtils.getUser;

@Controller
@RequestMapping("/manage/orders")
public class OrderManagementController {
    private OrderService orderService;

    @Autowired
    public OrderManagementController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("")
    public String showAllOrders(Model model) {
        model.addAttribute("statusList", OrderStatus.values());
        model.addAttribute("orderStrategies", OrderOrderingStrategy.values());
        return "order-management";
    }

    @GetMapping("/{orderId}")
    public String showOrderDetailsPage(@PathVariable Integer orderId,
                                       Authentication auth,
                                       Model model) {
        Order order = orderService.getOrder(orderId, getUser(auth));
        model.addAttribute("order", order);
        return "order-details";
    }
}
