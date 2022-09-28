package com.example.shopdemo.service.impl;

import com.example.shopdemo.service.StorageService;
import com.example.shopdemo.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class StorageServiceImpl implements StorageService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Path rootDirectory = Paths.get("./src/main/webapp");
    @Override
    public Path save(Path directory, MultipartFile file) throws IOException {
        // assign file name
        Path filePath;
        do {
            filePath = rootDirectory.resolve(directory.resolve(FileUtils.generateNewName(file.getOriginalFilename())));
        } while (Files.exists(filePath));

        // store
        logger.info("Saving " + filePath);
        Files.copy(file.getInputStream(), filePath);

        return rootDirectory.relativize(filePath);
    }

    @Override
    public void delete(Path filePath) throws IOException {
        filePath = rootDirectory.resolve(filePath);
        logger.info("Deleting " + filePath);
        Files.delete(filePath);
    }
}
