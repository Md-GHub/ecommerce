package com.mdghub.project.repository;

import com.mdghub.project.model.CartItems;
import com.mdghub.project.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
public interface CartItemsRepo extends JpaRepository<CartItems, Long> {
    @Query("SELECT ci FROM CartItems ci WHERE ci.cart.cartId = ?1 AND ci.product.productId = ?2")
    CartItems findCartItemByProductIdAndCartId(Long cartId, Long productId);

    @Modifying
    @Query("DELETE FROM CartItems ci WHERE ci.cart.cartId = ?1 AND ci.product.productId = ?2")
    void deleteCartItemByProductIdAndCartId(Long cartId, Long productId);

    @Modifying
    @Query("DELETE FROM CartItems ci WHERE ci.cart.cartId = ?1")
    void deleteCartItemByCartId(Long cartId);
}
