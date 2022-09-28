package com.example.shopdemo.converter;

import com.example.shopdemo.enumtype.ProductOrderingStrategy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductOrderingStrategyConverter implements Converter<String, ProductOrderingStrategy> {
    @Override
    public ProductOrderingStrategy convert(String source) {
        if (source == null) return null;
        try {
            ProductOrderingStrategy strategy = ProductOrderingStrategy.valueOf(source);
            return strategy;
        } catch (Exception e) {
            return null;
        }
    }
}
