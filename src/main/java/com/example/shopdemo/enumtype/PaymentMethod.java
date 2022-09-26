package com.example.shopdemo.enumtype;

public enum PaymentMethod {
    CASH_ON_DELIVERY;

    @Override
    public String toString() {
        switch (this) {
            case CASH_ON_DELIVERY:
                return "Case On Delivery (COD)";
            default:
                return "Unknown";
        }
    }
}
