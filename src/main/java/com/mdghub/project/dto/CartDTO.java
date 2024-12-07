package com.mdghub.project.dto;

import java.util.List;

public class CartDTO {
    private Long cartId;
    private Double totalPrice;
    private List<ProductDTO> products;

    public CartDTO() {}

    public CartDTO(Long cartId, Double totalPrice, List<ProductDTO> products) {
        this.cartId = cartId;
        this.totalPrice = totalPrice;
        this.products = products;
    }

    public CartDTO(Double totalPrice, List<ProductDTO> products) {
        this.totalPrice = totalPrice;
        this.products = products;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }
}
