package com.mdghub.project.repository;

import com.mdghub.project.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CategoryRepo extends JpaRepository<Category, Long> {
    public Category findByCategoryName(String categoryName);
}
