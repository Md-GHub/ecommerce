package com.mdghub.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(nullable = false)
    @Email
    private String email;

    @OneToMany(mappedBy = "order",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true)
    private List<OrderItems> orderItems = new ArrayList<>();

    private LocalDate orderDate;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    private Double totalAmount;
    private String orderStatus;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    public Order(String email, List<OrderItems> orderItems, LocalDate orderDate,
                 Double totalAmount, String orderStatus, Address address) {
        this.email = email;
        this.orderItems = orderItems;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.address = address;
    }

    public Order() {}

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<OrderItems> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItems> orderItems) {
        this.orderItems = orderItems;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
