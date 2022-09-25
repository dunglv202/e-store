package com.example.shopdemo.service;

import com.example.shopdemo.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;

public interface ProductService {
    Page<Product> getAllProducts(Pageable pagination);
    Product getProduct(Integer productId);

    Product addProduct(@Valid Product product);

    Product updateProduct(@Valid Product product);
    Product changeQuantity(Integer productId, Integer amount);

    Product deleteProduct(Integer productId);
}
