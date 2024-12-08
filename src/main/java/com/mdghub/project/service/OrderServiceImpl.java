package com.mdghub.project.service;

import com.mdghub.project.exceptions.APIException;
import com.mdghub.project.exceptions.ResourceNotFound;
import com.mdghub.project.model.*;
import com.mdghub.project.dto.OrderDTO;
import com.mdghub.project.dto.OrderItemsDTO;
import com.mdghub.project.repository.*;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    CartRepo cartRepository;

    @Autowired
    AddressRepo addressRepository;

    @Autowired
    OrderItemsRepo orderItemRepository;

    @Autowired
    OrderRepo orderRepository;

    @Autowired
    PaymentRepo paymentRepository;

    @Autowired
    CartService cartService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ProductRepo productRepository;

    @Override
    @Transactional
    public OrderDTO placeOrder(
            String emailId,
            Long addressId,
            String paymentMethod,
            String pgName,
            String pgPaymentId,
            String pgStatus,
            String pgResponseMessage) {

        Payment payment1 = paymentRepository.findByPgPaymentId(pgPaymentId);

        if(payment1!=null){
            throw new APIException("Order already been placed");
        }

        Cart cart = cartRepository.findCartByEmail(emailId);
        if (cart == null) {
            throw new APIException("Cart not found for email: " + emailId);
        }


        // Fetch and validate address
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFound("Address", "addressId", addressId));

        // Initialize order
        Order order = new Order();
        order.setEmail(emailId);
        order.setOrderDate(LocalDate.now());
        order.setTotalAmount(cart.getTotalPrice());
        order.setOrderStatus("Order Accepted!");
        order.setAddress(address);

        // Save payment details
        Payment payment = new Payment(paymentMethod, pgPaymentId, pgStatus, pgResponseMessage, pgName);
        payment.setOrder(order);
        payment = paymentRepository.save(payment);
        order.setPayment(payment);

        // Save order
        Order savedOrder = orderRepository.save(order);

        // Process cart items to order items
        List<OrderItems> orderItems = cart.getProducts().stream().map(cartItem -> {
            OrderItems orderItem = new OrderItems();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setDiscount(cartItem.getDiscount());
            orderItem.setOrderedProductPrice(cartItem.getProductPrice());
            orderItem.setOrder(savedOrder);
            return orderItem;
        }).collect(Collectors.toList());

        orderItems = orderItemRepository.saveAll(orderItems);

        // Update product stock and clear cart
        cart.getProducts().forEach(cartItem -> {
            Product product = cartItem.getProduct();
            product.setProductQuantity(product.getProductQuantity() - cartItem.getQuantity());
            productRepository.save(product);
        });

        cartService.clearCart(cart.getCartId()); // Implement this in cartService for batch cleanup.

        // Convert to DTO
        OrderDTO orderDTO = modelMapper.map(savedOrder, OrderDTO.class);
        orderItems.forEach(item -> orderDTO.getOrderItems().add(modelMapper.map(item, OrderItemsDTO.class)));
        orderDTO.setAddressId(addressId);

        return orderDTO;
    }

}
