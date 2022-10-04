package com.example.shopdemo.pojo;

import com.example.shopdemo.enumtype.OrderOrderingStrategy;
import com.example.shopdemo.enumtype.OrderStatus;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class OrderSpecs {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime from;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime to;
    private OrderOrderingStrategy sortedBy;
    private OrderStatus status;

    public LocalDateTime getFrom() {
        return from;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }

    public OrderOrderingStrategy getSortedBy() {
        return sortedBy;
    }

    public void setSortedBy(OrderOrderingStrategy sortedBy) {
        this.sortedBy = sortedBy;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
