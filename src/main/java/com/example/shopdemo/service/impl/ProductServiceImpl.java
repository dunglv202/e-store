package com.example.shopdemo.service.impl;

import com.example.shopdemo.entity.Brand;
import com.example.shopdemo.entity.Category;
import com.example.shopdemo.entity.Product;
import com.example.shopdemo.exception.NotFoundException;
import com.example.shopdemo.repository.ProductRepository;
import com.example.shopdemo.service.BrandService;
import com.example.shopdemo.service.CategoryService;
import com.example.shopdemo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Service
@Transactional
@Validated
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepo;
    private CategoryService categoryService;
    private BrandService brandService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepo, CategoryService categoryService, BrandService brandService) {
        this.productRepo = productRepo;
        this.categoryService = categoryService;
        this.brandService = brandService;
    }

    @Override
    public Page<Product> getAllProducts(Pageable pagination) {
        return productRepo.findAll(pagination);
    }

    @Override
    public Product getProduct(Integer productId) {
        return productRepo.findById(productId).orElseThrow(() -> new NotFoundException("Product - ID: " + productId));
    }

    @Override
    public Product addProduct(@Valid Product product) {
        // ensure new product is created
        product.setId(null);

        // set brand, category
        assemble(product);

        return productRepo.save(product);
    }

    @Override
    public Product updateProduct(@Valid Product product) {
        // check if product exists
        getProduct(product.getId());

        // set brand and category
        assemble(product);

        return productRepo.save(product);
    }

    @Override
    public Product deleteProduct(Integer productId) {
        // check if product exists
        Product product = getProduct(productId);

        productRepo.delete(product);

        return product;
    }

    private void assemble(Product product) {
        Category category = product.getCategory();
        if (category != null) {
            category = categoryService.getCategory(category.getId());
            product.setCategory(category);
        }

        Brand brand = product.getBrand();
        if (brand != null) {
            brand = brandService.getBrand(brand.getId());
            product.setBrand(brand);
        }
    }
}
