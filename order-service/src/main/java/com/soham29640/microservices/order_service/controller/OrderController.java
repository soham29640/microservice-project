package com.soham29640.microservices.order_service.controller;

import com.soham29640.microservices.order_service.dto.OrderRequest;
import com.soham29640.microservices.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest orderRequest){
        if (orderService.placeOrder(orderRequest) == null){
            return new ResponseEntity<>("Order is not present in Inventory",HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("Order Placed Successfully",HttpStatus.CREATED);
    }

}
