package com.mdghub.project.controller;

import com.mdghub.project.config.AppConstant;
import com.mdghub.project.dto.CategoryDTO;
import com.mdghub.project.dto.CategoryResponse;
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

    //Get All categories :
    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE) Integer pageSize,
            @RequestParam(name = "sortBy" , defaultValue = AppConstant.SORT_BY) String sortBy,
            @RequestParam(name = "sortOrder" , defaultValue = AppConstant.SORT_ORDER) String sortOrder
    ) {
        CategoryResponse categories = categoryService.getAllCategories(pageNumber,pageSize,sortBy,sortOrder);
        return ResponseEntity.ok(categories);
    }

    //Add categories :
    @PostMapping("/admin/categories")
    public ResponseEntity<CategoryDTO> addCategories(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO savedCategoryDTO = categoryService.addCategory(categoryDTO);
        return ResponseEntity.ok(savedCategoryDTO);
    }

    //Delete categories :
    @DeleteMapping("/admin/categories/{id}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable("id") Long id) {
        CategoryDTO categoryDTO = categoryService.deleteCategory(id);
        return ResponseEntity.ok(categoryDTO);
    }

    //Update categories :
    @PutMapping("/admin/categories/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable("id") Long id,
                                                 @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO c = categoryService.updateCategory(categoryDTO, id);
        return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
    }
}
