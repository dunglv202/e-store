package com.example.shopdemo.service.impl;

import com.example.shopdemo.entity.Category;
import com.example.shopdemo.exception.NotFoundException;
import com.example.shopdemo.repository.CategoryRepository;
import com.example.shopdemo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepo;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public Page<Category> getAllCategories(Pageable pagination) {
        return categoryRepo.findAll(pagination);
    }

    @Override
    public Category getCategory(Integer id) {
        return categoryRepo.findById(id).orElseThrow(() -> new NotFoundException("Category does not exist - ID: " + id));
    }

    @Override
    public Category addCategory(@Valid Category category) {
        // ensure new one is created
        category.setId(null);

        return categoryRepo.save(category);
    }

    @Override
    public Category updateCategory(@Valid Category category) {
        // check if cate exist
        getCategory(category.getId());

        return categoryRepo.save(category);
    }

    @Override
    public Category deleteCategory(Integer id) {
        // check if cate exists
        Category category = getCategory(id);

        categoryRepo.delete(category);

        return category;
    }
}
