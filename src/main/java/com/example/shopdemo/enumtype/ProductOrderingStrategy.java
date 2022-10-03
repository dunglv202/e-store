package com.example.shopdemo.enumtype;

public enum ProductOrderingStrategy {
    BY_NAME_ASC (true, "name_asc"),
    BY_NAME_DESC (false, "name_desc"),
    BY_PRICE_ASC (true, "price_asc"),
    BY_PRICE_DESC (false, "price_desc"),
    BY_RATING_ASC (true, "rating_asc"),
    BY_RATING_DESC (false, "rating_desc"),
    BY_OLDEST (true, "oldest"),
    BY_NEWEST (false, "newest");

    private boolean isAscending;
    private String paramValue;

    ProductOrderingStrategy(boolean isAscending, String paramValue) {
        this.isAscending = isAscending;
        this.paramValue = paramValue;
    }

    public boolean isAscending() {
        return isAscending;
    }

    @Override
    public String toString() {
        return paramValue;
    }
}
