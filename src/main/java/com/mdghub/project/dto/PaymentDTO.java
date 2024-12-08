package com.mdghub.project.dto;

import com.mdghub.project.model.Order;

public class PaymentDTO {

    private Long paymentId;
    private String PaymentMethod;
    private Order order;
    private String paypalPaymentId;
    private String paypalPaymentStatus;
    private String paypalResponseMessage;

    public PaymentDTO() {}
    public PaymentDTO(Long paymentId, String paymentMethod, Order order, String paypalPaymentId,
                      String paypalPaymentStatus, String paypalResponseMessage) {
        this.paymentId = paymentId;
        PaymentMethod = paymentMethod;
        this.order = order;
        this.paypalPaymentId = paypalPaymentId;
        this.paypalPaymentStatus = paypalPaymentStatus;
        this.paypalResponseMessage = paypalResponseMessage;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        PaymentMethod = paymentMethod;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getPaypalPaymentId() {
        return paypalPaymentId;
    }

    public void setPaypalPaymentId(String paypalPaymentId) {
        this.paypalPaymentId = paypalPaymentId;
    }

    public String getPaypalPaymentStatus() {
        return paypalPaymentStatus;
    }

    public void setPaypalPaymentStatus(String paypalPaymentStatus) {
        this.paypalPaymentStatus = paypalPaymentStatus;
    }

    public String getPaypalResponseMessage() {
        return paypalResponseMessage;
    }

    public void setPaypalResponseMessage(String paypalResponseMessage) {
        this.paypalResponseMessage = paypalResponseMessage;
    }
}
