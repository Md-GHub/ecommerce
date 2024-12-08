package com.mdghub.project.dto;

import com.mdghub.project.model.Order;
import com.mdghub.project.model.Product;


public class OrderItemsDTO {
    private Long orderId;
    private Product product;
    private Order order;
    private int quantity;
    private double discount;
    private double orderedProductPrice;

    public OrderItemsDTO() {
    }

    public OrderItemsDTO(Long orderId, Product product, Order order, int quantity, double discount, double orderedProductPrice) {
        this.orderId = orderId;
        this.product = product;
        this.order = order;
        this.quantity = quantity;
        this.discount = discount;
        this.orderedProductPrice = orderedProductPrice;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
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
