package com.example.shopdemo.enumtype;

public enum OrderOrderingStrategy {
    NO_ORDER (false),
    BY_NEWEST (false),
    BY_OLDEST (true);

    private boolean ascending;

    OrderOrderingStrategy(boolean ascending) {
        this.ascending = ascending;
    }

    public boolean isAscending() {
        return ascending;
    }
}
