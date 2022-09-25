package com.example.shopdemo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "cart_items")
public class CartItem {
    public static interface onCreation {}
    public static interface onUpdate {}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", updatable = false)
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", updatable = false)
    @NotNull(message = "Product is required", groups = {onCreation.class})
    private Product product;

    @Column(name = "quantity")
    @NotNull(message = "Quantity is required")
    @Positive(message = "Invalid quantity", groups = {onCreation.class, onUpdate.class})
    private Integer quantity;

    @Transient
    private Double total = 0.0;

    public CartItem() {
    }

    public CartItem(Integer id, User user, Product product, Integer quantity) {
        this.id = id;
        this.user = user;
        this.product = product;
        this.quantity = quantity;
        refreshTotal();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        refreshTotal();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
        refreshTotal();
    }

    public Double getTotal() {
        return refreshTotal();
    }

    private Double refreshTotal() {
        if (this.product == null || this.product.getPrice() == null || this.quantity == null)
            return -1.0;

        return this.total = this.product.getPrice() * this.quantity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}