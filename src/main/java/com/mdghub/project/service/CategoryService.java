package com.mdghub.project.service;

import com.mdghub.project.dto.CategoryDTO;
import com.mdghub.project.dto.CategoryResponse;
import com.mdghub.project.model.Category;

import java.util.List;

public interface CategoryService {
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize , String sortBy , String sortOrder);
    public CategoryDTO addCategory(CategoryDTO categoryDTO);
    public CategoryDTO deleteCategoryByCategoryId(Long id);
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long id);
}
