package com.example.shopdemo.service.impl;

import com.example.shopdemo.entity.CartItem;
import com.example.shopdemo.entity.User;
import com.example.shopdemo.exception.NotFoundException;
import com.example.shopdemo.repository.CartItemRepository;
import com.example.shopdemo.service.CartService;
import com.example.shopdemo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@Service
@Transactional
@Validated
public class CartServiceImpl implements CartService {
    private CartItemRepository itemRepo;
    private ProductService productService;

    @Autowired
    public CartServiceImpl(CartItemRepository itemRepo, ProductService productService) {
        this.itemRepo = itemRepo;
        this.productService = productService;
    }

    @Override
    public Page<CartItem> getAllItems(User user, Pageable pagination) {
        return itemRepo.findAllByUser(user, pagination);
    }

    @Override
    public CartItem getItem(Integer id, User user) {
        CartItem item = itemRepo.findById(id).orElse(null);

        if (item==null || !item.getUser().equals(user))
            throw new NotFoundException("Cart item not exists - ID: " + id);

        return item;
    }

    @Override
    @Validated({CartItem.onCreation.class})
    public CartItem addItem(@Valid CartItem item, User user) {
        // check if product was already in user's cart
        Optional<CartItem> foundByProduct = itemRepo.findByUserAndProductId(user, item.getProduct().getId());
        if (foundByProduct.isPresent())
            return foundByProduct.get();

        // add to cart
        item.setId(null);
        item.setUser(user);
        item.setProduct(productService.getProduct(item.getProduct().getId()));
        return itemRepo.save(item);
    }

    @Override
    @Validated({CartItem.onUpdate.class})
    public CartItem updateItem(@Valid CartItem item, User user) {
        // check if item exists and owner's request
        CartItem old = getItem(item.getId(), user);

        item.setProduct(old.getProduct());
        return itemRepo.save(item);
    }

    @Override
    public CartItem deleteItem(Integer id, User user) {
        // check for existence and owner's request
        CartItem found = getItem(id, user);
        itemRepo.delete(found);
        return found;
    }
}
