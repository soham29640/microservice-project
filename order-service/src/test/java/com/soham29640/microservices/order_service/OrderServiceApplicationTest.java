package com.soham29640.microservices.order_service;

import com.soham29640.microservices.order_service.client.InventoryClient;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.mysql.MySQLContainer;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderServiceApplicationTest {

	@ServiceConnection()
	static MySQLContainer mysql = new MySQLContainer("mysql:8.3.0");

	static {
		mysql.start();
	}

	@MockitoBean
	private InventoryClient inventoryClient;

	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup(){
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	@Test
	void InventoryTest() {

		when(inventoryClient.isInStock("iphone_15", 67))
				.thenReturn(true);

		String body = """
				{
				    "skuCode":"iphone_15",
				    "price":9000,
				    "quantity":67
				}""";

		given()
				.contentType("application/json")
				.body(body)
				.when()
				.post("/api/order")
				.then()
				.statusCode(201)
				.body(equalTo("Order Placed Successfully"));
	}

}
