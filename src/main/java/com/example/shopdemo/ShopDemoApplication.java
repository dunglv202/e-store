package com.example.shopdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ShopDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopDemoApplication.class, args);
    }

}
