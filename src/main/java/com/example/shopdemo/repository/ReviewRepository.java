package com.example.shopdemo.repository;

import com.example.shopdemo.entity.Product;
import com.example.shopdemo.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    Page<Review> findAllByOrderItem_Product(Product product, Pageable pagination);
    boolean existsByOrderItemId(Integer orderItemId);
    Optional<Review> findByOrderItemId(Integer orderItemId);
}
