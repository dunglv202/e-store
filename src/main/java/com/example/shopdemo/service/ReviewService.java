package com.example.shopdemo.service;

import com.example.shopdemo.entity.OrderItem;
import com.example.shopdemo.entity.Review;
import com.example.shopdemo.entity.User;
import org.springframework.data.domain.Page;

public interface ReviewService {
    Page<Review> getAllReviews(Integer productId);
    Review getReview(Integer id);

    Review addReview(OrderItem orderItem, User user);

    Review updateReview(Review review);

    Review deleteReview(Integer id);
}
