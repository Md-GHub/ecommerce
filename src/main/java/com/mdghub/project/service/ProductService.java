package com.mdghub.project.service;


import com.mdghub.project.dto.ProductDTO;
import com.mdghub.project.dto.ProductResponse;
import com.mdghub.project.model.Product;

public interface ProductService {
    public ProductResponse getAllProducts();
    public ProductDTO addProduct(Long id, ProductDTO productDTO);


    ProductResponse searchByCategory(Long id);

    ProductResponse searchProductByKeyword(String keyword);

    ProductDTO updateProductDetails(Product product, Long productId);

    void deleteProductById(Long productId);
}
