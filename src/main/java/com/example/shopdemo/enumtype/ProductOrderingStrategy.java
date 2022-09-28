package com.example.shopdemo.enumtype;

public enum ProductOrderingStrategy {
    BY_NAME_ASC (true),
    BY_NAME_DESC (false),
    BY_PRICE_ASC (true),
    BY_PRICE_DESC (false),
    BY_RATING_ASC (true),
    BY_RATING_DESC (false);

    private boolean isAscending;

    ProductOrderingStrategy(boolean isAscending) {
        this.isAscending = isAscending;
    }

    public boolean isAscending() {
        return isAscending;
    }
}
