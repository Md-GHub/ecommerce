package com.mdghub.project.controller;

import com.mdghub.project.dto.OrderDTO;
import com.mdghub.project.dto.OrderRequestDTO;
import com.mdghub.project.service.OrderService;
import com.mdghub.project.utils.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private AuthUtil authUtil;

    @PostMapping("/order/users/payments/{paymentMethod}")
    public ResponseEntity<?> orderProducts(@PathVariable String paymentMethod, @RequestBody OrderRequestDTO orderRequestDTO) {

            String emailId = authUtil.loggedInEmail();
            System.out.println(emailId);
            System.out.println(orderRequestDTO.getAddressId());

            OrderDTO order = orderService.placeOrder(
                    emailId,
                    orderRequestDTO.getAddressId(),
                    paymentMethod,
                    orderRequestDTO.getPgName(),
                    orderRequestDTO.getPgPaymentId(),
                    orderRequestDTO.getPgStatus(),
                    orderRequestDTO.getPgResponseMessage()
            );

            return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

}
