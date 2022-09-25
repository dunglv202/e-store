package com.example.shopdemo.rest;

import com.example.shopdemo.entity.Order;
import com.example.shopdemo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.shopdemo.util.AuthenticationUtils.getUser;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderRestController {
    private OrderService orderService;

    @Autowired
    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("")
    public List<Order> getAllOrders(@RequestParam Integer page,
                                    @RequestParam Integer size,
                                    Authentication auth) {
        return orderService.getAllOrders(getUser(auth), PageRequest.of(page, size)).toList();
    }

    @GetMapping("/{orderId}")
    public Order getOrder(@PathVariable Integer orderId,
                          Authentication auth) {
        return orderService.getOrder(orderId, getUser(auth));
    }

    @PostMapping("")
    public Order makeOrder(@RequestBody Order order,
                           Authentication auth) {
        return orderService.makeOrder(order, getUser(auth));
    }

    @DeleteMapping("/{orderId}")
    public Order cancelOrder(@PathVariable Integer orderId,
                             Authentication auth) {
        return orderService.cancelOrder(orderId, getUser(auth));
    }
}
