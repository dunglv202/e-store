package com.example.shopdemo.service.impl;

import com.example.shopdemo.entity.OrderItem;
import com.example.shopdemo.entity.Review;
import com.example.shopdemo.entity.User;
import com.example.shopdemo.service.OrderService;
import com.example.shopdemo.service.ReviewService;
import org.springframework.data.domain.Page;

public class ReviewServiceImpl implements ReviewService {
    @Override
    public Page<Review> getAllReviews(Integer productId) {
        return null;
    }

    @Override
    public Review getReview(Integer id) {
        return null;
    }

    @Override
    public Review addReview(OrderItem orderItem, User user) {
        return null;
    }

    @Override
    public Review updateReview(Review review) {
        return null;
    }

    @Override
    public Review deleteReview(Integer id) {
        return null;
    }
}
