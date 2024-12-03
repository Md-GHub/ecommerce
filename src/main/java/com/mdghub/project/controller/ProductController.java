package com.mdghub.project.controller;

import com.mdghub.project.dto.ProductDTO;
import com.mdghub.project.dto.ProductResponse;
import com.mdghub.project.model.Product;
import com.mdghub.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class ProductController {

    private ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/public/products")
    public ResponseEntity<ProductResponse> getProducts() {
        ProductResponse productResponse = productService.getAllProducts();
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }


    @GetMapping(value = "/public/categories/{id}/products")
    public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable("id") Long id) {
        ProductResponse productResponse = productService.searchByCategory(id);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/public/product/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyword(@PathVariable("keyword") String keyword) {
        ProductResponse productResponse = productService.searchProductByKeyword(keyword);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }
    @PostMapping(value = "/admin/categories/{categoriesId}/product")
    public ResponseEntity<ProductDTO> addProduct(@PathVariable("categoriesId") Long categoriesId,
                                                 @RequestBody ProductDTO productDTO) {
        ProductDTO savedProductDTO = productService.addProduct(categoriesId,productDTO);
        return ResponseEntity.ok(savedProductDTO);
    }
    @PutMapping(value = "/products/{productId}")
    public ResponseEntity<ProductDTO> updateProductDetails(@RequestBody Product product,
                                                           @PathVariable("productId") Long productId) {
        System.out.println(product.getProductPrice());
        ProductDTO productDTO = productService.updateProductDetails(product,productId);
        return ResponseEntity.ok(productDTO);
    }


    @DeleteMapping(value = "/admin/products/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable("productId") Long productId) {
        productService.deleteProductById(productId);
        return ResponseEntity.ok("Product deleted successfully");
    }


}
