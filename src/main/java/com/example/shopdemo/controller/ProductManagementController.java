package com.example.shopdemo.controller;

import com.example.shopdemo.entity.Product;
import com.example.shopdemo.service.BrandService;
import com.example.shopdemo.service.CategoryService;
import com.example.shopdemo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
@RequestMapping("/manage/products")
public class ProductManagementController {
    private BrandService brandService;
    private CategoryService categoryService;
    private ProductService productService;

    @Autowired
    public ProductManagementController(BrandService brandService, CategoryService categoryService, ProductService productService) {
        this.brandService = brandService;
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("")
    public String showManagementPage() {
        return "product-management";
    }

    @GetMapping("/add")
    public String showProductForm(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories(PageRequest.of(0, 1000)));
        model.addAttribute("brands", brandService.getAllBrands(PageRequest.of(0, 1000)));
        model.addAttribute("product", new Product());
        model.addAttribute("images", new CommonsMultipartFile[100]);
        return "product-form";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute("product") Product product,
                             @RequestParam("images") MultipartFile[] images) {
        product = productService.addProduct(product);
        productService.addAllImages(product.getId(), images);
        return "redirect:edit/" + product.getId();
    }

    @GetMapping("/edit/{productId}")
    public String showUpdateForm(@PathVariable Integer productId,
                                 Model model) {
        model.addAttribute("product", productService.getProduct(productId));
        model.addAttribute("images", productService.getAllImages(productId, PageRequest.of(0, 20)));
        model.addAttribute("categories", categoryService.getAllCategories(PageRequest.of(0, 1000)));
        model.addAttribute("brands", brandService.getAllBrands(PageRequest.of(0, 1000)));

        model.addAttribute("uploadedImage", new CommonsMultipartFile[100]);
        return "product-form-edit";
    }

    @PostMapping("/edit/{productId}")
    public String doUpdate(@ModelAttribute("product") Product product,
                           @RequestParam("uploadedImage") MultipartFile[] images,
                           @PathVariable Integer productId) {
        product.setId(productId);
        productService.updateProduct(product);
        productService.addAllImages(product.getId(), images);
        return ("redirect:" + productId + "?success");
    }
}
