package com.example.shopdemo.service.impl;

import com.example.shopdemo.entity.*;
import com.example.shopdemo.enumtype.OrderStatus;
import com.example.shopdemo.exception.NotFoundException;
import com.example.shopdemo.pojo.Mail;
import com.example.shopdemo.pojo.OrderSpecs;
import com.example.shopdemo.repository.CartItemRepository;
import com.example.shopdemo.repository.OrderRepository;
import com.example.shopdemo.service.MailService;
import com.example.shopdemo.service.OrderService;
import com.example.shopdemo.service.ProductService;
import com.example.shopdemo.spec.OrderSpecifications;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.transaction.Transactional;
import javax.validation.Valid;

import static com.example.shopdemo.spec.OrderSpecifications.matchesSpec;

@Service
@Transactional
@Validated
public class OrderServiceImpl implements OrderService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private OrderRepository orderRepo;
    private ProductService productService;
    private CartItemRepository cartItemRepo;
    private MailService mailService;
    private TemplateEngine templateEngine;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepo, ProductService productService, CartItemRepository cartItemRepo, MailService mailService, TemplateEngine templateEngine) {
        this.orderRepo = orderRepo;
        this.productService = productService;
        this.cartItemRepo = cartItemRepo;
        this.mailService = mailService;
        this.templateEngine = templateEngine;
    }

    @Override
    public Page<Order> getAllOrders(User user, OrderSpecs specs, Pageable pagination) {
        return orderRepo.findAll(OrderSpecifications.ofUser(user).and(matchesSpec(specs)), pagination);
    }

    @Override
    public Order getOrder(Integer id, User user) {
        Order found = orderRepo.findById(id).orElse(null);
        if (found==null || !found.getUser().equals(user))
            throw new NotFoundException("Order does not exist - ID: " + id);

        return found;
    }

    @Override
    public Order makeOrder(@Valid Order order, User user) {
        // assign order to user
        order.setUser(user);

        // validate order items
        processOrder(order);

        // save order as new order
        order.setId(null);
        order.setStatus(OrderStatus.PENDING);
        order = orderRepo.save(order);

        // send confirmation email
        sendConfirmationEmail(order, "order-confirmation");

        return order;
    }

    private void processOrder(Order order) {
        order.getItems().forEach(item -> {
            // get product
            Product product = productService.getProduct(item.getProduct().getId());
            // change product quantity in stock
            productService.changeQuantity(product.getId(), -item.getQuantity());
            // remove product from cart if exists
            CartItem cartItem = cartItemRepo.findByUserAndProductId(order.getUser(), product.getId()).orElse(null);
            if (cartItem != null)
                cartItemRepo.delete(cartItem);
            // save item
            item.setId(null);
            item.setProduct(product);
            item.setOrder(order);
        });
    }

    @Override
    public Order updateStatus() {
        return null;
    }

    @Override
    public Order cancelOrder(Integer id, User user) {
        Order found = getOrder(id, user);
        // check is order status is valid for canceling
        if (found.getStatus() != OrderStatus.PENDING)
            throw new UnsupportedOperationException("Confirmed order couldn't be canceled");

        // cancel (delete) order
        orderRepo.delete(found);

        // get products back to stock
        found.getItems().forEach(item -> {
            productService.changeQuantity(item.getProduct().getId(), +item.getQuantity());
        });

        return found;
    }

    private void sendConfirmationEmail(Order order, String template) {
        Context context = new Context();
        context.setVariable("order", order);

        String renderedTemplate = templateEngine.process(template, context);

        Mail mail = new Mail();
        mail.setRecipient(order.getUser().getDetails().getEmail());
        mail.setSubject("Your order at TheHeckShop.com is being processed #" + order.getId());
        mail.setContent(renderedTemplate);

        try {
            mailService.send(mail);
        } catch (Exception e) {
            logger.error("Couldn't send email");
            e.printStackTrace();
        }
    }
}
