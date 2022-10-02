package com.example.shopdemo.service.impl;

import com.example.shopdemo.entity.Brand;
import com.example.shopdemo.entity.Category;
import com.example.shopdemo.entity.Product;
import com.example.shopdemo.entity.ProductImage;
import com.example.shopdemo.exception.InsufficientException;
import com.example.shopdemo.exception.NotFoundException;
import com.example.shopdemo.exception.UnsupportedFileType;
import com.example.shopdemo.pojo.ProductSpecs;
import com.example.shopdemo.repository.ProductImageRepository;
import com.example.shopdemo.repository.ProductRepository;
import com.example.shopdemo.service.BrandService;
import com.example.shopdemo.service.CategoryService;
import com.example.shopdemo.service.ProductService;
import com.example.shopdemo.service.StorageService;
import com.example.shopdemo.spec.ProductSpecifications;
import com.example.shopdemo.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.example.shopdemo.util.FileUtils.FileType.IMAGE;

@Service
@Transactional
@Validated
public class ProductServiceImpl implements ProductService {
    private final Path imageDirectory = Paths.get("images");
    private ProductRepository productRepo;
    private ProductImageRepository imageRepo;
    private StorageService storageService;
    private CategoryService categoryService;
    private BrandService brandService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepo, ProductImageRepository imageRepo, StorageService storageService, CategoryService categoryService, BrandService brandService) {
        this.productRepo = productRepo;
        this.imageRepo = imageRepo;
        this.storageService = storageService;
        this.categoryService = categoryService;
        this.brandService = brandService;
    }

    @Override
    public Page<Product> getAllProducts(ProductSpecs specs, Pageable pagination) {
        return productRepo.findAll(ProductSpecifications.matchesSpec(specs), pagination);
    }

    @Override
    public Product getProduct(Integer productId) {
        return productRepo.findById(productId).orElseThrow(() -> new NotFoundException("Product not exists - ID: " + productId));
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
        Product found = getProduct(product.getId());

        // set brand and category
        assemble(product);
        found.merge(product);

        return productRepo.save(found);
    }

    @Override
    public Product changeQuantity(Integer productId, Integer amount) {
        Product product = getProduct(productId);
        if (product.getQuantity() + amount < 0)
            throw new InsufficientException("Insufficient quantity for " + product.getName() + ", in stock: " + product.getQuantity());
        product.setQuantity(product.getQuantity() + amount);
        return productRepo.save(product);
    }

    @Override
    public Page<ProductImage> getAllImages(Integer productId, Pageable pagination) {
        Product product = getProduct(productId);
        return imageRepo.findAllByProduct(product, pagination);
    }

    private ProductImage addImage(Integer productId, MultipartFile image) {
        Product product = getProduct(productId);

        // check if image file format is supported
        if (!FileUtils.isOfType(image.getOriginalFilename(), IMAGE))
            throw new UnsupportedFileType(FileUtils.getExtension(Objects.requireNonNull(image.getOriginalFilename())));

        // save file to db and directory
        Path imagePath;
        try {
            imagePath = storageService.save(imageDirectory, image);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't save image: " + e.getMessage());
        }

        return imageRepo.save(new ProductImage(null, imagePath.toString().replaceAll("\\\\", "/"), product));
    }

    @Override
    public List<ProductImage> addAllImages(Integer productId, MultipartFile... images) {
        if (images == null || images.length == 0 || images[0].getOriginalFilename().equals("")) return null;

        List<ProductImage> imageList = new ArrayList<>();
        Arrays.stream(images).forEach((image) -> {
            imageList.add(addImage(productId, image));
        });

        return imageList;
    }

    private ProductImage deleteImage(Integer imageId) {
        ProductImage found = imageRepo.findById(imageId).orElseThrow(() -> new NotFoundException("Image does not exists - ID:" + imageId));

        // delete from database
        imageRepo.delete(found);

        // delete from storage
        try {
            storageService.delete(Paths.get(found.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't delete image: " + e.getMessage());
        }

        return found;
    }

    @Override
    public ProductImage deleteImage(Integer productId, Integer imageId) {
        ProductImage found = imageRepo.findById(imageId).orElseThrow(() -> new NotFoundException("Image does not exists - ID:" + imageId));

        if (!found.getProduct().getId().equals(productId))
            throw new NotFoundException("No image with ID: " + imageId + " was found for product - ID: " + productId);

        return deleteImage(imageId);
    }

    private void deleteAllImages(Integer productId) {
        imageRepo.findAllByProductId(productId).forEach((image) -> {
            deleteImage(image.getId());
        });
    }

    @Override
    public Product deleteProduct(Integer productId) {
        // check if product exists
        Product product = getProduct(productId);

        productRepo.delete(product);
        deleteAllImages(productId);

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
