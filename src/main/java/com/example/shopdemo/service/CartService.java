package com.example.shopdemo.service;

import com.example.shopdemo.entity.CartItem;
import com.example.shopdemo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;

public interface CartService {
    Page<CartItem> getAllItems(User user, Pageable pagination);
    CartItem getItem(Integer id, User user);

    CartItem addItem(@Valid CartItem item, User user);

    CartItem updateItem(@Valid CartItem item, User user);

    CartItem deleteItem(Integer id, User user);
}
