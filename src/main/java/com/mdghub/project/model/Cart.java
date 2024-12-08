package com.mdghub.project.model;


import jakarta.persistence.*;

import java.util.Arrays;
import java.util.List;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cartId;
    private Double totalPrice;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Users user;

    @OneToMany( mappedBy = "cart" ,
                cascade ={CascadeType.PERSIST, CascadeType.MERGE,CascadeType.REMOVE},
                orphanRemoval = true)
    private List<CartItems> products;

    public Cart(Double totalPrice, Users user, List<CartItems> products) {
        this.totalPrice = totalPrice;
        this.user = user;
        this.products = products;
    }

    public Cart(){}

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

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public List<CartItems> getProducts() {
        return products;
    }

    public void setProducts(List<CartItems> products) {
        this.products = products;
    }


}
