package com.example.shopdemo.service;

import com.example.shopdemo.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;

public interface CategoryService {
    Page<Category> getAllCategories(Pageable pagination);
    Category getCategory(Integer id);

    Category addCategory(@Valid Category category);

    Category updateCategory(@Valid Category category);

    Category deleteCategory(Integer id);
}
