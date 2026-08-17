package com.soham29640.mycroservices.product_service.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductRequest {
    String id;
    String name;
    String description;
    BigDecimal price;
}
