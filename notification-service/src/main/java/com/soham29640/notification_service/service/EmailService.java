package com.soham29640.notification_service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(String orderNumber, String email) {

        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(email);
        mail.setSubject("Order Confirmation - "+ orderNumber);

        mail.setText(
                "Hello,\n\n" +

                        "Thank you for shopping with Spring Shop.\n\n" +

                        "This email confirms that we have received your order and it has been successfully placed.\n\n" +

                        "Order Details:\n" +
                        "Order Number: " + orderNumber + "\n\n" +

                        "We will send you another email as your order is processed and shipped.\n\n" +

                        "If you have any questions about your order, simply reply to this email and our support team will be happy to assist you.\n\n" +

                        "Thank you for choosing Spring Shop.\n\n" +

                        "Best regards,\n" +
                        "Spring Shop\n" +
                        "Customer Support"
        );

        javaMailSender.send(mail);
    }
}