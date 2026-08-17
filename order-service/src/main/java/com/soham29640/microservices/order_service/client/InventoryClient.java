package com.soham29640.microservices.order_service.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClient;

//@FeignClient(
//        value = "inventory",
//        url = "${inventory.url}")
//public interface InventoryClient {
//
//    @RequestMapping(method = RequestMethod.GET, value = "/api/inventory")
//    boolean isInStock(@RequestParam String skuCode,@RequestParam Integer quantity);
//}

@Component
public class InventoryClient {

    private final RestClient restClient;

    @Value("${inventory.url}")
    private String inventoryUrl;

    public InventoryClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public boolean isInStock(String skuCode, Integer quantity) {

        return restClient
                .get()
                .uri(inventoryUrl + "/api/inventory?skuCode={skuCode}&quantity={quantity}", skuCode, quantity)
                .retrieve()
                .body(Boolean.class);
    }
}

