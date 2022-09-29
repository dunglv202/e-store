package com.example.shopdemo.repository;

import com.example.shopdemo.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer>, JpaSpecificationExecutor<OrderItem> {

}
