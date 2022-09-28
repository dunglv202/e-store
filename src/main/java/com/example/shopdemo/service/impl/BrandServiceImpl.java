package com.example.shopdemo.service.impl;

import com.example.shopdemo.entity.Brand;
import com.example.shopdemo.exception.NotFoundException;
import com.example.shopdemo.repository.BrandRepository;
import com.example.shopdemo.service.BrandService;
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
public class BrandServiceImpl implements BrandService {
    private BrandRepository brandRepo;

    @Autowired
    public BrandServiceImpl(BrandRepository brandRepo) {
        this.brandRepo = brandRepo;
    }

    @Override
    public Page<Brand> getAllBrands(Pageable pagination) {
        return brandRepo.findAll(pagination);
    }

    @Override
    public Brand getBrand(Integer id) {
        return brandRepo.findById(id).orElseThrow(() -> new NotFoundException("Brand does not exist - ID: " + id));
    }

    @Override
    public Brand addBrand(@Valid Brand brand) {
        // ensure new one is created
        brand.setId(null);

        return brandRepo.save(brand);
    }

    @Override
    public Brand updateBrand(@Valid Brand brand) {
        // check if exists
        getBrand(brand.getId());

        return brandRepo.save(brand);
    }

    @Override
    public Brand deleteBrand(Integer id) {
        // check if exists
        Brand brand = getBrand(id);

        brandRepo.delete(brand);

        return brand;
    }
}
