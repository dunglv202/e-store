package com.example.shopdemo.service;

import com.example.shopdemo.entity.Review;
import com.example.shopdemo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;

public interface ReviewService {
    Page<Review> getAllReviews(Integer productId, Pageable pagination);
    Review getReview(Integer id);

    Review addReview(@Valid Review review, User user);

    Review updateReview(@Valid Review review, User user);

    Review deleteReview(Integer id, User user);
}
