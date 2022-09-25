package com.example.shopdemo.service;

import com.example.shopdemo.entity.Order;
import com.example.shopdemo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;

public interface OrderService {
    Page<Order> getAllOrders(User user, Pageable pagination);
    Order getOrder(Integer id, User user);

    Order makeOrder(@Valid Order order, User user);

    Order updateStatus();

    Order cancelOrder(Integer id, User user);
}
