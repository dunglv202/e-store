package com.example.shopdemo.rest;

import com.example.shopdemo.entity.OrderItem;
import com.example.shopdemo.entity.Review;
import com.example.shopdemo.entity.User;
import com.example.shopdemo.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.shopdemo.util.AuthenticationUtils.getUser;

@RestController
public class ReviewController {
    private ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/api/v1/products/{productId}/reviews")
    public List<Review> getAllProductReviews(@RequestParam Integer page,
                                             @RequestParam Integer size,
                                             @PathVariable Integer productId) {
        return reviewService.getAllReviews(productId, PageRequest.of(page, size)).toList();
    }

    @PostMapping("/api/v1/reviews")
    public Review leaveReview(@RequestBody Review review,
                              Authentication auth) {
        return reviewService.addReview(review, getUser(auth));
    }

    @PutMapping("/api/v1/reviews/{reviewId}")
    public Review editReview(@PathVariable Integer reviewId,
                             @RequestBody Review review,
                             Authentication auth) {
        review.setId(reviewId);
        return reviewService.updateReview(review, getUser(auth));
    }

    @DeleteMapping("/api/v1/reviews/{reviewId}")
    public Review editReview(@PathVariable Integer reviewId,
                             Authentication auth) {
        return reviewService.deleteReview(reviewId, getUser(auth));
    }
}
