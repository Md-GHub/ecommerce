package com.mdghub.project.repository;

import com.mdghub.project.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long>{
    @Query("SELECT c FROM Cart c WHERE c.user.email = :email")
    Cart findByEmail(String email);
}
