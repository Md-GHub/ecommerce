package com.mdghub.project.repository;

import com.mdghub.project.model.Category;
import com.mdghub.project.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    Product findByProductName(String productName);

    List<Product> findByCategoryOrderByProductPriceAsc(Category category);

    List<Product> findByProductNameLikeIgnoreCase(String keyword);
}
