package com.example.shopdemo.enumtype;

public enum OrderStatus {
    PENDING,
    REFUSED,
    PREPARING,
    DELIVERING,
    RECEIVED;

    public boolean isNextOf(OrderStatus s) {
        switch (s) {
            case PENDING:
                return this==REFUSED || this==PREPARING;
            case PREPARING:
                return this==DELIVERING;
            case DELIVERING:
                return this==RECEIVED || this==REFUSED;
            case RECEIVED:
                return false;
            case REFUSED:
                return this==PREPARING;
        }
        return false;
    }
}
