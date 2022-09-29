package com.example.shopdemo.service.impl;

import com.example.shopdemo.entity.*;
import com.example.shopdemo.enumtype.OrderStatus;
import com.example.shopdemo.exception.NotFoundException;
import com.example.shopdemo.exception.UnauthorizedException;
import com.example.shopdemo.repository.OrderItemRepository;
import com.example.shopdemo.repository.ReviewRepository;
import com.example.shopdemo.service.ProductService;
import com.example.shopdemo.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@Validated
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository reviewRepo;
    private ProductService productService;
    private OrderItemRepository orderItemRepo;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepo, ProductService productService, OrderItemRepository orderItemRepo) {
        this.reviewRepo = reviewRepo;
        this.productService = productService;
        this.orderItemRepo = orderItemRepo;
    }

    @Override
    public Page<Review> getAllReviews(Integer productId, Pageable pagination) {
        Product product = productService.getProduct(productId);
        return reviewRepo.findAllByOrderItem_Product(product, pagination);
    }

    @Override
    public Review getReview(Integer id) {
        return null;
    }

    @Override
    public Review addReview(@Valid Review review, User user) {
        // check if order item exists
        System.err.println(orderItemRepo.findById(49).isPresent());
        Optional<OrderItem> orderItem = orderItemRepo.findById(review.getOrderItem().getId());
        if (!orderItem.isPresent() || !orderItem.get().getOrder().getUser().equals(user))
            throw new NotFoundException("Order item not found - ID: " + review.getOrderItem().getId());

        // check if order was created on last 60 days
        Order order = orderItem.get().getOrder();
        LocalDateTime prev60Days = LocalDate.now().minusDays(60).atStartOfDay();
        if (order.getStatus() == OrderStatus.RECEIVED && order.getDateCreated().isBefore(prev60Days))
            throw new UnauthorizedException("You are not allowed to leave a review after 60 days since creating order");

        review.setOrderItem(orderItem.get());
        return reviewRepo.save(review);
    }

    @Override
    public Review updateReview(@Valid Review review, User user) {
        // check if review exists
        Review found = reviewRepo.findById(review.getId()).orElseThrow(() -> new NotFoundException("Review not found - ID: " + review.getId()));
        if (!found.getOrderItem().getOrder().getUser().equals(user))
            throw new UnauthorizedException("Only owner can update their review");

        found.merge(review);

        return reviewRepo.save(found);
    }

    @Override
    public Review deleteReview(Integer id, User user) {
        // check if review exists
        Review found = reviewRepo.findById(id).orElseThrow(() -> new NotFoundException("Review not found - ID: " + id));
        if (!found.getOrderItem().getOrder().getUser().equals(user))
            throw new UnauthorizedException("Only owner can delete their review");

        reviewRepo.delete(found);
        return found;
    }
}
