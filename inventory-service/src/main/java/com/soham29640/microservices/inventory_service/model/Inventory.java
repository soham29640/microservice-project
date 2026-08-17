package com.soham29640.microservices.inventory_service.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="inventory")
@Data
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String skuCode;
    private String quantity;
}
