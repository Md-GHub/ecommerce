package com.mdghub.project.model;

import jakarta.persistence.*;

@Entity
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne
    @JoinColumn
    private Order order;

    private Long quantity;
    private double discount;
    private double orderedProductPrice;

    public OrderItems(Order order, Long quantity, double discount, double orderedProductPrice, Product product) {
        this.order = order;
        this.quantity = quantity;
        this.discount = discount;
        this.orderedProductPrice = orderedProductPrice;
        this.product = product;
    }

    public OrderItems() {

    }


    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getOrderedProductPrice() {
        return orderedProductPrice;
    }

    public void setOrderedProductPrice(double orderedProductPrice) {
        this.orderedProductPrice = orderedProductPrice;
    }
}
