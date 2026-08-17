package com.soham29640.notification_service;

import com.soham29640.notification_service.service.EmailService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled
@SpringBootTest
class EmailServiceTests {

    @Autowired
    private EmailService emailService;

	@Test
	void testEmailSend() {
		emailService.sendEmail("cart711","soham29640@gmail.com");
	}

}
