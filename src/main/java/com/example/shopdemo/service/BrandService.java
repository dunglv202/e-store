package com.example.shopdemo.service;

import com.example.shopdemo.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;

public interface BrandService {
    Page<Brand> getAllBrands(Pageable pagination);
    Brand getBrand(Integer id);

    Brand addBrand(@Valid Brand brand);

    Brand updateBrand(@Valid Brand brand);

    Brand deleteBrand(Integer id);
}
