package com.soham29640.microservices.inventory_service.repository;

import com.soham29640.microservices.inventory_service.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    boolean existsBySkuCodeAndQuantityGreaterThanEqual(
            String skuCode,
            Integer quantity
    );
}