package com.example.shopdemo.repository;

import com.example.shopdemo.entity.Product;
import com.example.shopdemo.entity.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
    List<ProductImage> findAllByProductId(Integer productId);
    Page<ProductImage> findAllByProduct(Product product, Pageable pagination);
}
