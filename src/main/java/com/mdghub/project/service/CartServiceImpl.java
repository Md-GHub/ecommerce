package com.mdghub.project.service;

import com.mdghub.project.dto.CartDTO;
import com.mdghub.project.dto.ProductDTO;
import com.mdghub.project.exceptions.APIException;
import com.mdghub.project.exceptions.ResourceNotFound;
import com.mdghub.project.model.Cart;
import com.mdghub.project.model.CartItems;
import com.mdghub.project.model.Product;
import com.mdghub.project.repository.CartItemsRepo;
import com.mdghub.project.repository.CartRepo;
import com.mdghub.project.repository.ProductRepo;
import com.mdghub.project.utils.AuthUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CartServiceImpl implements CartService {


    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CartItemsRepo cartItemsRepo;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private AuthUtil authUtil; //after  logedin can access usersdata

    @Override
    public CartDTO addProduct(long productId, int quantity) {
        // Create or find the existing cart
        Cart cart = createCart();

        // Retrieve product details
        Product product = productRepo.findByProductId(productId)
                .orElseThrow(() -> new ResourceNotFound("Product", "Product Id", productId));

        // Validate stock and check if the product already exists in the cart

        if (product.getProductQuantity() == 0) {
            throw new APIException("Product " + product.getProductName() + " is out of stock.");
        }
        if (product.getProductQuantity() < quantity) {
            throw new APIException("Only " + product.getProductQuantity() + " units of " + product.getProductName() + " are available.");
        }

        // Create a new CartItem
        CartItems newCartItem = new CartItems();
        newCartItem.setProduct(product);
        newCartItem.setQuantity(quantity);
        newCartItem.setDiscount(product.getDiscount());
        newCartItem.setCart(cart);
        newCartItem.setProductPrice(product.getProductSpecialPrice());
        cartItemsRepo.save(newCartItem);

        // Update product stock
        product.setProductQuantity(product.getProductQuantity() - quantity);
        productRepo.save(product);

        // Update cart total price
        double totalItemPrice = product.getProductSpecialPrice() * quantity;
        cart.setTotalPrice(cart.getTotalPrice() + totalItemPrice);
        cartRepo.save(cart);

        // Convert Cart to CartDTO
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        List<CartItems> cartItems = cart.getProducts();

        List<ProductDTO> productDTOList = cartItems.stream()
                .map(item -> {
                    ProductDTO productDTO = modelMapper.map(item.getProduct(), ProductDTO.class);
                    productDTO.setQuantity(item.getQuantity());
                    return productDTO;
                })
                .collect(Collectors.toList());

        cartDTO.setProducts(productDTOList);

        return cartDTO;
    }

    @Override
    public CartDTO getCart() {
        //fetch the email :
        String email = authUtil.loggedInEmail();

        //fetch the cart by using users id
        Cart cart = cartRepo.findByEmail(email);

        //convert the cart into cartDTO
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

        //convert the List of products from the product entity via cartItems
        List<ProductDTO> productDTO = cart.getProducts().stream()
                .map(product->{
                    ProductDTO productDTO1 = modelMapper.map(product, ProductDTO.class);
                    productDTO1.setQuantity(product.getQuantity());
                    return productDTO1;
                }).toList();
        //return the CartDTO
        cartDTO.setProducts(productDTO);

        return cartDTO;
    }

    @Override
    public CartDTO updateProductQuantityInCart(Long productId, int quantity) {
        String emailId = authUtil.loggedInEmail();
        Cart userCart = cartRepo.findByEmail(emailId);
        Long cartId  = userCart.getCartId();

        Cart cart = cartRepo.findById(cartId)
                .orElseThrow(() -> new ResourceNotFound("Cart", "cartId", cartId));

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFound("Product", "productId", productId));

        if (product.getProductQuantity() == 0) {
            throw new APIException(product.getProductName() + " is not available");
        }

        if (product.getProductQuantity() < quantity) {
            throw new APIException("Please, make an order of the " + product.getProductName()
                    + " less than or equal to the quantity " + product.getProductQuantity() + ".");
        }

        CartItems cartItem = cartItemsRepo.findCartItemsByProduct_IdAndCart_Id(cartId, productId);

        if (cartItem == null) {
            throw new APIException("Product " + product.getProductName() + " not available in the cart!!!");
        }

        cartItem.setProductPrice(product.getProductSpecialPrice());
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cartItem.setDiscount(product.getDiscount());
        cart.setTotalPrice(cart.getTotalPrice() + (cartItem.getProductPrice() * quantity));
        cartRepo.save(cart);
        CartItems updatedItem = cartItemsRepo.save(cartItem);
        if(updatedItem.getQuantity() == 0){
            cartItemsRepo.deleteById(updatedItem.getCart().getCartId());
        }


        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

        List<CartItems> cartItems = cart.getProducts();

        Stream<ProductDTO> productStream = cartItems.stream().map(item -> {
            ProductDTO prd = modelMapper.map(item.getProduct(), ProductDTO.class);
            prd.setQuantity(item.getQuantity());
            return prd;
        });


        cartDTO.setProducts(productStream.toList());

        return cartDTO;
    }

    @Override
    public String deleteProductFromCart(Long cartId, Long productId) {
        Cart cart = cartRepo.findById(cartId)
                .orElseThrow(() -> new ResourceNotFound("Cart", "cartId", cartId));

        CartItems cartItem = cartItemsRepo.findCartItemsByProduct_IdAndCart_Id(cartId, productId);

        if (cartItem == null) {
            throw new ResourceNotFound("Product", "productId", productId);
        }

        cart.setTotalPrice(cart.getTotalPrice() -
                (cartItem.getProductPrice() * cartItem.getQuantity()));

        cartItemsRepo.deleteCartItemByProductIdAndCartId(cartId, productId);

        return "Product " + cartItem.getProduct().getProductName() + " removed from the cart !!!";
    }


    // checking for the existing cart if it is available
    // return it or else create new and add attributes
    private Cart createCart() {
        Cart cart = cartRepo.findByEmail((authUtil.loggedInEmail()));
        if (cart != null) {
            return cart;
        }
        cart = new Cart();
        cart.setTotalPrice(0.00);
        cart.setUser(authUtil.loggedInUser());
        Cart newCart = cartRepo.save(cart);

        return newCart;
    }
}
