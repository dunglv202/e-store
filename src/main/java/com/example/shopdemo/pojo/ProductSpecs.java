package com.example.shopdemo.pojo;

import com.example.shopdemo.enumtype.ProductOrderingStrategy;

public class ProductSpecs {
    private String keyword;
    private String category;
    private String brand;
    private Double minPrice;
    private Double maxPrice;
    private ProductOrderingStrategy sortedBy;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public ProductOrderingStrategy getSortedBy() {
        return sortedBy;
    }

    public void setSortedBy(ProductOrderingStrategy sortedBy) {
        this.sortedBy = sortedBy;
    }
}
