package com.example.shopdemo.rest;

import com.example.shopdemo.entity.Category;
import com.example.shopdemo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryRestController {
    private CategoryService categoryService;

    @Autowired
    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public List<Category> getAllCategories(@RequestParam Integer page,
                                    @RequestParam Integer size) {
        return categoryService.getAllCategories(PageRequest.of(page, size)).toList();
    }

    @PostMapping("")
    public Category addCategory(@RequestBody Category category) {
        return categoryService.addCategory(category);
    }

    @PutMapping("/{categoryId}")
    public Category updateCategory(@PathVariable Integer categoryId,
                             @RequestBody Category category) {
        category.setId(categoryId);
        return categoryService.updateCategory(category);
    }

    @DeleteMapping("/{categoryId}")
    public Category deleteCategory(@PathVariable Integer categoryId) {
        return categoryService.deleteCategory(categoryId);
    }
}
