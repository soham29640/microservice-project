package com.soham29640.mycroservices.product_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductResponse {
    String id;
    String name;
    String Description;
    BigDecimal price;

}
