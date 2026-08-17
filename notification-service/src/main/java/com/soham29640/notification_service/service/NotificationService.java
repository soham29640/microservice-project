package com.soham29640.notification_service.service;

import com.soham29640.notification_service.order.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    @Autowired
    private EmailService emailService;

    @KafkaListener(topics = "order-placed")
    public void listen(OrderPlacedEvent event) {

        log.info("Received message by Kafka consumer");
        try{
            emailService.sendEmail(event.getOrderNumber(),event.getEmail());
            log.info("Message is sent Successfully");
        }
        catch(Exception e){
            log.error("Exception while sendEmail");
        }

    }

}