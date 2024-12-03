package com.mdghub.project.service;

import com.mdghub.project.dto.ProductDTO;
import com.mdghub.project.dto.ProductResponse;
import com.mdghub.project.exceptions.ResourceNotFound;
import com.mdghub.project.model.Category;
import com.mdghub.project.model.Product;
import com.mdghub.project.repository.CategoryRepo;
import com.mdghub.project.repository.ProductRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;


    //fetching list of products :
    @Override
    public ProductResponse getAllProducts() {
        List<Product> products = productRepo.findAll();
        List<ProductDTO> productDTO = products.stream()
                .map(product -> modelMapper.map(product,ProductDTO.class))
                .toList();
        return new ProductResponse(productDTO);
    }

    //fetch list of products by category :
    @Override
    public ProductResponse searchByCategory(Long id) {
        Category category = categoryRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFound("Category","Category id",id));

        List<Product> products = productRepo.findByCategoryOrderByProductPriceAsc(category);
        List<ProductDTO> productDTO = products.stream()
                .map(product -> modelMapper.map(product,ProductDTO.class))
                .toList();
        return new ProductResponse(productDTO);
    }

    //fetch list of products by keyword :
    @Override
    public ProductResponse searchProductByKeyword(String keyword) {
        keyword = "%"+keyword+"%";
        List<Product> products = productRepo.findByProductNameLikeIgnoreCase(keyword);
        System.out.println(products.size());
        List<ProductDTO> productDTO = products.stream()
                .map(product -> modelMapper.map(product,ProductDTO.class))
                .toList();
        return new ProductResponse(productDTO);
    }

    @Override
    public ProductDTO updateProductDetails(Product product, Long productId) {
        //check for the product availability :
        Product productFromDB = productRepo.findById(productId)
                .orElseThrow(()-> new ResourceNotFound("Product","Product id",productId));
        //update the value :
        productFromDB.setProductName(product.getProductName());

        productFromDB.setProductDescription(product.getProductDescription());
        productFromDB.setImage(product.getImage());
        productFromDB.setCategory(product.getCategory());
        productFromDB.setProductQuantity(product.getProductQuantity());

        Double productSpecialPrice = product.getProductPrice()-(product.getDiscount()*0.01)*product.getProductPrice();
        productFromDB.setProductPrice(product.getProductPrice());
        productFromDB.setDiscount(product.getDiscount());
        productFromDB.setProductSpecialPrice(productSpecialPrice);
        //save the new model :
        Product savedProduct = productRepo.save(productFromDB);
        return modelMapper.map(savedProduct,ProductDTO.class);


    }

    @Override
    public void deleteProductById(Long productId) {
        //check for the product availability :
        Product productFromDB = productRepo.findById(productId)
                .orElseThrow(()-> new ResourceNotFound("Product","Product id",productId));
        //delete the product :
        productRepo.delete(productFromDB);
    }

    //adding products using category :
    @Override
    public ProductDTO addProduct(Long id, ProductDTO productDTO) {
//        if(productRepo.findByProductName(product.getProductName())!=null){
//            return
//        }
        Category category = categoryRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFound("Category","Category id",id));

        Product product = modelMapper.map(productDTO,Product.class);
        product.setImage("default.img");
        product.setCategory(category);
        Double discountPrice = product.getProductPrice()-(product.getDiscount()*0.01)*product.getProductPrice();
        product.setProductSpecialPrice(discountPrice);
        Product savedProduct = productRepo.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }


}
