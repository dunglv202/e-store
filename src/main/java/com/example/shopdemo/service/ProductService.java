package com.example.shopdemo.service;

import com.example.shopdemo.entity.Product;
import com.example.shopdemo.entity.ProductImage;
import com.example.shopdemo.pojo.ProductSpecs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

public interface ProductService {
    Page<Product> getAllProducts(ProductSpecs specs, Pageable pagination);
    Product getProduct(Integer productId);

    Product addProduct(@Valid Product product);

    Product updateProduct(@Valid Product product);
    Product changeQuantity(Integer productId, Integer amount);
    Page<ProductImage> getAllImages(Integer productId, Pageable pagination);
    List<ProductImage> addAllImages(Integer productId, MultipartFile... images);
    ProductImage deleteImage(Integer productId, Integer imageId);

    Product deleteProduct(Integer productId);
}
