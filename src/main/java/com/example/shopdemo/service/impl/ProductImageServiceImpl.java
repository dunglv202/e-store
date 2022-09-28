package com.example.shopdemo.service.impl;

import com.example.shopdemo.entity.Product;
import com.example.shopdemo.entity.ProductImage;
import com.example.shopdemo.exception.NotFoundException;
import com.example.shopdemo.exception.UnsupportedFileType;
import com.example.shopdemo.repository.ProductImageRepository;
import com.example.shopdemo.service.ProductImageService;
import com.example.shopdemo.service.ProductService;
import com.example.shopdemo.service.StorageService;
import com.example.shopdemo.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
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
public class ProductImageServiceImpl implements ProductImageService {
    private final Path imageDirectory = Paths.get("images");
    private final ProductImageRepository imageRepo;
    private final ProductService productService;
    private final StorageService storageService;

    @Autowired
    public ProductImageServiceImpl(ProductImageRepository imageRepo, ProductService productService, StorageService storageService) {
        this.imageRepo = imageRepo;
        this.productService = productService;
        this.storageService = storageService;
    }

    @Override
    public Page<ProductImage> getAllImages(Integer productId, Pageable pagination) {
        return imageRepo.findAllByProductId(productId, pagination);
    }

    @Override
    public List<ProductImage> addAllImages(Integer productId, MultipartFile[] images) {
        if (images == null) return null;

        List<ProductImage> imageList = new ArrayList<>();
        Arrays.stream(images).forEach((image) -> {
            imageList.add(addImage(productId, image));
        });

        return imageList;
    }

    @Override
    public ProductImage addImage(Integer productId, MultipartFile image) {
        Product product = productService.getProduct(productId);

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
    public ProductImage deleteImage(Integer productId, Integer id) {
        ProductImage found = imageRepo.findById(id).orElseThrow(() -> new NotFoundException("Image does not exists - ID:" + id));
        if (!found.getProduct().getId().equals(productId))
            throw new NotFoundException("No image with ID: " + id + " was found for product - ID: " + productId);

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
}
