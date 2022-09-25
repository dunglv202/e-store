package com.example.shopdemo.repository;

import com.example.shopdemo.entity.CartItem;
import com.example.shopdemo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    Page<CartItem> findAllByUser(User user, Pageable pagination);
    Optional<CartItem> findByUserAndProductId(User user, Integer productId);
}
