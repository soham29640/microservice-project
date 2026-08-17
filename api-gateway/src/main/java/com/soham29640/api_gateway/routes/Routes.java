package com.soham29640.api_gateway.routes;

import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.setPath;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;

@Configuration
public class Routes {

    // ==================== PRODUCT ====================

    @Bean
    public RouterFunction<ServerResponse> productServiceRoute() {
        return route("product_service")
                .route(
                        RequestPredicates.path("/api/product/**"),
                        HandlerFunctions.http()
                )
                .before(uri("http://localhost:8080"))
                .filter(
                        CircuitBreakerFilterFunctions.circuitBreaker
                                (
                                config -> config
                                        .setId("productServiceCircuitBreaker")
                                        .setFallbackUri("forward:/fallbackRoute")
                                )
                )
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> productServiceSwaggerRoute() {
        return route("product_service_swagger")
                .route(
                        RequestPredicates.path("/aggregate/product-service/api-docs"),
                        HandlerFunctions.http()
                )
                .before(uri("http://localhost:8080"))
                .before(setPath("/api-docs"))
                .build();
    }


    // ==================== ORDER ====================

    @Bean
    public RouterFunction<ServerResponse> orderServiceRoute() {
        return route("order_service")
                .route(
                        RequestPredicates.path("/api/order/**"),
                        HandlerFunctions.http()
                )
                .before(uri("http://localhost:8081"))
                .filter(
                        CircuitBreakerFilterFunctions.circuitBreaker(
                                config -> config
                                        .setId("orderServiceCircuitBreaker")
                                        .setFallbackUri("forward:/fallbackRoute")
                        )
                )
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> orderServiceSwaggerRoute() {
        return route("order_service_swagger")
                .route(
                        RequestPredicates.path(
                                "/aggregate/order-service/api-docs"
                        ),
                        HandlerFunctions.http()
                )
                .before(uri("http://localhost:8081"))
                .before(setPath("/api-docs"))
                .build();
    }


    // ==================== INVENTORY ====================

    @Bean
    public RouterFunction<ServerResponse> inventoryServiceRoute() {
        return route("inventory_service")
                .route(
                        RequestPredicates.path("/api/inventory/**"),
                        HandlerFunctions.http()
                )
                .before(uri("http://localhost:8082"))
                .filter(
                        CircuitBreakerFilterFunctions.circuitBreaker(
                                config -> config
                                        .setId("inventoryServiceCircuitBreaker")
                                        .setFallbackUri("forward:/fallbackRoute")
                        )
                )
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> inventoryServiceSwaggerRoute() {
        return route("inventory_service_swagger")
                .route(
                        RequestPredicates.path(
                                "/aggregate/inventory-service/api-docs"
                        ),
                        HandlerFunctions.http()
                )
                .before(uri("http://localhost:8082"))
                .before(setPath("/api-docs"))
                .build();
    }


    // ==================== FALLBACK ====================

    @Bean
    public RouterFunction<ServerResponse> fallBackRoute() {
        return route("fallbackRoute")
                .GET(
                        "/fallbackRoute",
                        request -> ServerResponse
                                .status(HttpStatus.SERVICE_UNAVAILABLE)
                                .body("Service Unavailable, please try again later")
                )
                .build();
    }
}