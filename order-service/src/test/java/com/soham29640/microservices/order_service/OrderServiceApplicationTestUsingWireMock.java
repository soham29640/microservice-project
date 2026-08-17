package com.soham29640.microservices.order_service;

import com.github.tomakehurst.wiremock.matching.StringValuePattern;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderServiceApplicationTestUsingWireMock {

    @ServiceConnection
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.3.0");

    static {
        mysql.start();
    }

    @RegisterExtension
    static WireMockExtension wireMock =
            WireMockExtension.newInstance()
                    .options(options().dynamicPort())
                    .build();


    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("inventory.url", wireMock::baseUrl);
    }

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void inventoryTest() {

        wireMock.stubFor(
                get(urlPathEqualTo("/api/inventory"))
                        .withQueryParam("skuCode", equalTo("iphone_15"))
                        .withQueryParam("quantity", equalTo("67"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
                                        .withBody("true")
                        )
        );

        String body = """
                {
                    "skuCode":"iphone_15",
                    "price":9000,
                    "quantity":67
                }
                """;

        given()
                .contentType("application/json")
                .body(body)
                .when()
                .post("/api/order")
                .then()
                .statusCode(201)
                .body(org.hamcrest.Matchers.equalTo("Order Placed Successfully"));
    }
}