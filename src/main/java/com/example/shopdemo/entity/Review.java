package com.example.shopdemo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review {
    public static interface OnCreation{}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "rating")
    @NotNull(message = "Rating is required")
    @Range(min = 1, max = 10, message = "Rating must be between 1 and 10")
    private Integer rating;

    @Column(name = "comment")
    private String comment;

    @Column(name = "date_created")
    @CreationTimestamp
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime dateCreated;

    @OneToOne
    @JoinColumn(name = "order_item_id", updatable = false)
    @NotNull(message = "Order item must be specified for reviews", groups = {OnCreation.class})
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private OrderItem orderItem;

    public Review() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    public String getUser() {
        return orderItem.getOrder().getUser().getUsername();
    }

    public void merge(Review review) {
        this.rating = review.rating == null ? this.rating : review.rating;
        this.comment = review.comment == null ? this.comment : review.comment;
    }
}
