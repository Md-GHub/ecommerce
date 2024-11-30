package com.mdghub.project.service;

import com.mdghub.project.model.Category;

import java.util.List;

public interface CategoryService {
    public List<Category> getAllCategories();
    public String addCategory(Category category);
    public String deleteCategory(Long id);
    public Category updateCategory(Category category, Long id);
}
