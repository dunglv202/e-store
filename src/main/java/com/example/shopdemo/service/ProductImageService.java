package com.example.shopdemo.service;

import com.example.shopdemo.entity.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductImageService {
    Page<ProductImage> getAllImages(Integer productId, Pageable pagination);

    List<ProductImage> addAllImages(Integer productId, MultipartFile[] images);
    ProductImage addImage(Integer productId, MultipartFile image);

    ProductImage deleteImage(Integer productId, Integer id);
}
