package com.example.shopdemo.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public interface StorageService {
    Path save(Path directory, MultipartFile file) throws IOException;
    void delete(Path file) throws IOException;
}
