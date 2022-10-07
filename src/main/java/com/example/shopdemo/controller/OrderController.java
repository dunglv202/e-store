package com.example.shopdemo.controller;

import com.example.shopdemo.entity.Order;
import com.example.shopdemo.entity.OrderItem;
import com.example.shopdemo.entity.Review;
import com.example.shopdemo.service.OrderService;
import com.example.shopdemo.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.shopdemo.util.AuthenticationUtils.getUser;

@Controller
@RequestMapping("/order")
public class OrderController {
    private OrderService orderService;
    private ReviewService reviewService;

    @Autowired
    public OrderController(OrderService orderService, ReviewService reviewService) {
        this.orderService = orderService;
        this.reviewService = reviewService;
    }

    @GetMapping("/history")
    public String showOrderHistoryPage() {
        return "order-history";
    }

    @GetMapping("/history/{orderId}")
    public String showOrderDetailsPage(@PathVariable Integer orderId,
                                       Authentication auth,
                                       Model model) {
        model.addAttribute("order", orderService.getOrder(orderId, getUser(auth)));
        return "order-details";
    }

    @GetMapping("/history/{orderId}/review")
    public String showReviewPage(@PathVariable Integer orderId,
                                 Authentication auth,
                                 Model model) {
        Order order = orderService.getOrder(orderId, getUser(auth));
        if (order.getDateCreated().isBefore(LocalDateTime.now().minusDays(30)))
            model.addAttribute("nonEditable", true);

        Map<OrderItem, Review> itemReviewMap = new HashMap<>();
        order.getItems().forEach(i -> itemReviewMap.put(i, reviewService.getReviewForItem(i.getId())));
        model.addAttribute("itemReviews", itemReviewMap);
        return "review";
    }
}
