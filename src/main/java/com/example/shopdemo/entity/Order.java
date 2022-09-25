package com.example.shopdemo.entity;

import com.example.shopdemo.enumtype.OrderStatus;
import com.example.shopdemo.enumtype.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @Size(min = 1, message = "Order must contain at least 1 item")
    private List<@Valid OrderItem> items = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipient_id")
    @NotNull(message = "Recipient is required")
    @Valid
    private Recipient recipient;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "payment_method")
    @NotNull(message = "Payment method is required")
    private PaymentMethod payment;

    @Column(name = "notes")
    private String notes;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @Column(name = "date_created", updatable = false)
    @CreationTimestamp
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime dateCreated;

    @Transient
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double total;

    public Order() {
    }

    public Order(Integer id, User user, List<OrderItem> items, Recipient recipient, PaymentMethod payment, String notes, OrderStatus status, LocalDateTime dateCreated) {
        this.id = id;
        this.user = user;
        this.items = items;
        this.recipient = recipient;
        this.payment = payment;
        this.notes = notes;
        this.status = status;
        this.dateCreated = dateCreated;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public PaymentMethod getPayment() {
        return payment;
    }

    public void setPayment(PaymentMethod payment) {
        this.payment = payment;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Double getTotal() {
        return items.stream().map(OrderItem::getTotal).reduce(Double::sum).orElse(-1.0);
    }
}
