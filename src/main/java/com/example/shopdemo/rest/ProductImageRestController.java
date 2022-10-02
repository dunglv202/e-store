package com.example.shopdemo.rest;

import com.example.shopdemo.entity.ProductImage;
import com.example.shopdemo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products/{productId}/images")
public class ProductImageRestController {
    private ProductService productService;

    @Autowired
    public ProductImageRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public List<ProductImage> getAllImages(@RequestParam Integer page,
                                           @RequestParam Integer size,
                                           @PathVariable Integer productId) {
        return productService.getAllImages(productId, PageRequest.of(page,size)).toList();
    }

    @PostMapping("")
    public List<ProductImage> addAllImages(@PathVariable Integer productId,
                                           @RequestParam("files") MultipartFile[] files) {
        return productService.addAllImages(productId, files);
    }

    @DeleteMapping("/{imageId}")
    public ProductImage deleteImage(@PathVariable Integer productId,
                                    @PathVariable Integer imageId) {
        return productService.deleteImage(productId, imageId);
    }
}
