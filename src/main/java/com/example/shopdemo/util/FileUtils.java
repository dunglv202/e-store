package com.example.shopdemo.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class FileUtils {
    public static enum FileType {
        IMAGE ("jpg", "png", "jpeg", "webp"),
        MS_EXCEL ("csv", "xlsx");

        private Set<String> supportedExtensions = new HashSet<>();

        FileType(String... extensions) {
            Collections.addAll(supportedExtensions, extensions);
        }

        private boolean supports(String fileExtension) {
            return supportedExtensions.contains(fileExtension);
        }
    }

    public static String generateNewName(String oldName) {
        return UUID.randomUUID().toString() + "." + getExtension(oldName);
    }

    public static String getExtension(String fileName) {
        int lastDotIdx = fileName.lastIndexOf('.');
        return fileName.substring(lastDotIdx+1).toLowerCase();
    }

    public static boolean isOfType(String fileName, FileType fileType) {
        return fileType.supports(getExtension(fileName));
    }

}
