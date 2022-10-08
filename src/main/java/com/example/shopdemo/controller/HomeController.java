package com.example.shopdemo.controller;

import com.example.shopdemo.pojo.ProductSpecs;
import com.example.shopdemo.service.BrandService;
import com.example.shopdemo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private BrandService brandService;
    private CategoryService categoryService;

    @Autowired
    public HomeController(BrandService brandService, CategoryService categoryService) {
        this.brandService = brandService;
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public String showHomepage(ProductSpecs specs, Model model) {
        model.addAttribute("specs", specs);
        model.addAttribute("brands", brandService.getAllBrands(PageRequest.of(0, 100)));
        model.addAttribute("categories", categoryService.getAllCategories(PageRequest.of(0, 100)));
        return "index";
    }
}
