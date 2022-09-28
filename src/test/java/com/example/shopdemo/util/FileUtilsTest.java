package com.example.shopdemo.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilsTest {

    @Test
    void getExtension() {
        assertEquals("txt", FileUtils.getExtension("file.tXt"));
    }
}