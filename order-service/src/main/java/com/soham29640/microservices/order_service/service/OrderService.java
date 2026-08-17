package com.soham29640.microservices.order_service.service;

import com.soham29640.microservices.order_service.client.InventoryClient;
import com.soham29640.microservices.order_service.dto.OrderRequest;
import com.soham29640.microservices.order_service.dto.OrderResponse;
import com.soham29640.microservices.order_service.event.OrderPlacedEvent;
import com.soham29640.microservices.order_service.model.Order;
import com.soham29640.microservices.order_service.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InventoryClient inventoryClient;

    private final KafkaTemplate<String,OrderPlacedEvent> kafkaTemplate;

    public OrderService(KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public OrderResponse placeOrder(OrderRequest orderRequest) {

        var isProductInStock = inventoryClient.isInStock(orderRequest.getSkuCode(),orderRequest.getQuantity());

        if (isProductInStock){
            Order order = new Order();

            order.setOrderNumber(UUID.randomUUID().toString());
            order.setPrice(orderRequest.getPrice());
            order.setSkuCode(orderRequest.getSkuCode());
            order.setQuantity(orderRequest.getQuantity());

            orderRepository.save(order);

            OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent(order.getOrderNumber(),orderRequest.getEmail());
            logger.info("message is send from Kafka consumer");
            kafkaTemplate.send("order-placed",orderPlacedEvent);
            logger.info(" --- End --- ");

            OrderResponse orderResponse = new OrderResponse();

            orderResponse.setOrderNumber(order.getOrderNumber());
            orderResponse.setPrice(order.getPrice());
            orderResponse.setSkuCode(order.getSkuCode());
            orderResponse.setQuantity(order.getQuantity());

            return orderResponse;
        }
        return null;
    }
}