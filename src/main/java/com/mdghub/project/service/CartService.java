package com.mdghub.project.service;

import com.mdghub.project.dto.CartDTO;
import org.springframework.stereotype.Service;


public interface CartService{
    public CartDTO addProduct(long productId, int quantity);

    public CartDTO getCart();

    CartDTO updateProductQuantityInCart(Long productId, int delete);

    String deleteProductFromCart(Long cartId, Long productId);
}
