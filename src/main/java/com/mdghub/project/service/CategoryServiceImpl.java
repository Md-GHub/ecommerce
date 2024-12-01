package com.mdghub.project.service;

import com.mdghub.project.dto.CategoryDTO;
import com.mdghub.project.dto.CategoryResponse;
import com.mdghub.project.exceptions.APIException;
import com.mdghub.project.exceptions.ResourceNotFound;
import com.mdghub.project.model.Category;
import com.mdghub.project.repository.CategoryRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {


    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    //fetching all categories :
    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize,
                                             String sortBy , String sortOrder) {

        Sort sort = sortBy.equals("asc")? Sort.by(sortOrder).ascending():
                                          Sort.by(sortOrder).descending();
        //pagination :
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Category> categoriesPage = categoryRepo.findAll(pageable);

        List<Category> categories = categoriesPage.getContent();
        if(categories.isEmpty()){
            throw new APIException("No Category Found");
        }


        List<CategoryDTO> categoryDTO = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();
        CategoryResponse categoryResponse = new CategoryResponse(categoryDTO);
        categoryResponse.setPageNumber(categoriesPage.getNumber());
        categoryResponse.setPageSize(categoriesPage.getSize());
        categoryResponse.setTotalElements(categories.size());
        categoryResponse.setTotalPages(categoriesPage.getTotalPages());
        categoryResponse.setLastPage(categoriesPage.isLast());
        return categoryResponse;
    }

    //adding category :
    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDTO) {
        Category c = categoryRepo.findByCategoryName(categoryDTO.getCategoryName());
        if(c != null) {
            throw new APIException("Category of name "+categoryDTO.getCategoryName()+" already exists!");
        }
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepo.save(category);
        CategoryDTO savedCategoryDTO = modelMapper.map(savedCategory, CategoryDTO.class);
        return savedCategoryDTO;
    }

    //delete category by id :
    @Transactional
    @Override
    public CategoryDTO deleteCategory(Long id) {
        Optional<Category> categories = categoryRepo.findById(id);
        Category category = categories
                .orElseThrow(() ->
                        new ResourceNotFound("Category","Category Id",id)
                );
        categoryRepo.delete(category);
        CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);
        return categoryDTO;
    }

    //update category by id :
    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long id) {
        Optional<Category> categories = categoryRepo.findById(id);
        Category category = categories
                .orElseThrow(() ->
                        new ResourceNotFound("Category","Category Id",id)
                );
        categoryDTO.setId(id);
        Category saveCategory = modelMapper.map(categoryDTO, Category.class);
        Category saved = categoryRepo.save(saveCategory);
        CategoryDTO savedCategoryDTO = modelMapper.map(saved, CategoryDTO.class);
        return savedCategoryDTO;
    }
}
