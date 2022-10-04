package com.example.shopdemo.enumtype;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderStatusTest {
    @Test
    void getNex() {
        OrderStatus a = OrderStatus.PENDING;
        System.out.println(a.ordinal());
    }
}