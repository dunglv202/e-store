package com.example.shopdemo.rest;

import com.example.shopdemo.entity.Product;
import com.example.shopdemo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductRestController {
    private ProductService productService;

    @Autowired
    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public List<Product> getAllProducts(@RequestParam Integer page,
                                        @RequestParam Integer size) {
        return productService.getAllProducts(PageRequest.of(page, size)).toList();
    }

    @GetMapping("/{productId}")
    public Product getProduct(@PathVariable Integer productId) {
        return productService.getProduct(productId);
    }

    @PostMapping()
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @PutMapping("/{productId}")
    public Product updateProduct(@PathVariable Integer productId,
                                 @RequestBody Product product) {
        product.setId(productId);
        return productService.updateProduct(product);
    }

    @DeleteMapping("/{productId}")
    public Product deleteProduct(@PathVariable Integer productId) {
        return productService.deleteProduct(productId);
    }
}
