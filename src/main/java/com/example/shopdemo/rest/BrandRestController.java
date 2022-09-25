package com.example.shopdemo.rest;

import com.example.shopdemo.entity.Brand;
import com.example.shopdemo.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/brands")
public class BrandRestController {
    private BrandService brandService;

    @Autowired
    public BrandRestController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("")
    public List<Brand> getAllBrands(@RequestParam Integer page,
                                    @RequestParam Integer size) {
        return brandService.getAllBrands(PageRequest.of(page, size)).toList();
    }

    @PostMapping("")
    public Brand addBrand(@RequestBody Brand brand) {
        return brandService.addBrand(brand);
    }

    @PutMapping("/{brandId}")
    public Brand updateBrand(@PathVariable Integer brandId,
                             @RequestBody Brand brand) {
        brand.setId(brandId);
        return brandService.updateBrand(brand);
    }

    @DeleteMapping("/{brandId}")
    public Brand deleteBrand(@PathVariable Integer brandId) {
        return brandService.deleteBrand(brandId);
    }
}
