package com.example.shopdemo.controller;

import com.example.shopdemo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{productId}")
    public String showProductDetailsPage(@PathVariable Integer productId,
                                         Model model) {
        model.addAttribute("product", productService.getProduct(productId));
        model.addAttribute("images", productService.getAllImages(productId, PageRequest.of(0, 10)));
        return "product-details";
    }
}
