package com.mdghub.project.service;

import com.mdghub.project.dto.CartDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CartService{
    CartDTO addProductToCart(Long productId, Long quantity);

    List<CartDTO> getAllCarts();

    CartDTO getCart(String emailId, Long cartId);

    @Transactional
    CartDTO updateProductQuantityInCart(Long productId, Integer quantity);

    String deleteProductFromCart(Long cartId, Long productId);

    void updateProductInCarts(Long cartId, Long productId);

    void clearCart(Long cartId);
}
