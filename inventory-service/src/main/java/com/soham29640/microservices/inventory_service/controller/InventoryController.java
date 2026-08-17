package com.soham29640.microservices.inventory_service.controller;

import com.soham29640.microservices.inventory_service.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public boolean isInStock(String skuCode,Integer quantity){
        return inventoryService.isInStock(skuCode, quantity);
    }
}
