package com.mdghub.project.controller;

import com.mdghub.project.model.Category;
import com.mdghub.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/public/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @PostMapping("/admin/categories")
    public ResponseEntity<String> addCategories(@Valid @RequestBody Category category) {
        categoryService.addCategory(category);
        return ResponseEntity.ok("Category added successfully");
    }

    @DeleteMapping("/admin/categories/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long id) {
        try{
            categoryService.deleteCategory(id);
            return ResponseEntity.ok("Category deleted successfully");
        }catch (Exception e) {
            return new ResponseEntity<>("No category available", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/admin/categories/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable("id") Long id,
                                                 @RequestBody Category category) {
        try{
            Category c = categoryService.updateCategory(category, id);
            return new ResponseEntity<>("updated ", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("No data found", HttpStatus.NOT_FOUND);
        }
    }
}
