package com.example.shopdemo.converter;

import com.example.shopdemo.enumtype.ProductOrderingStrategy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

@Component
public class ProductOrderingStrategyConverter implements Converter<String, ProductOrderingStrategy> {
    @Override
    public ProductOrderingStrategy convert(String source) {
        return ProductOrderingStrategy.valueOf("BY_" + source.toUpperCase());
    }
}
