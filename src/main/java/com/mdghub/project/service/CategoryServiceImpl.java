package com.mdghub.project.service;

import com.mdghub.project.model.Category;
import com.mdghub.project.repository.CategoryRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {


    private CategoryRepo categoryRepo;
    @Autowired
    public CategoryServiceImpl(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public String addCategory(Category category) {
        categoryRepo.save(category);
        return "success";
    }

    @Transactional
    @Override
    public String deleteCategory(Long id) {
        Optional<Category> categories = categoryRepo.findById(id);
        Category category = categories
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,"No user founded")
                );
        categoryRepo.delete(category);
        return "Removed successfully";
    }

    @Override
    public Category updateCategory(Category category, Long id) {
        Optional<Category> categories = categoryRepo.findById(id);
        Category savedCategory = categories
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,"No user founded")
                );
        category.setCategoryId(id);
        Category saved = categoryRepo.save(category);
        return saved;
    }
}
