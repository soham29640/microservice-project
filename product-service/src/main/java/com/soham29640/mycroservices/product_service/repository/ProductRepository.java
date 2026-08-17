package com.soham29640.mycroservices.product_service.repository;

import com.soham29640.mycroservices.product_service.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product,String> {

}
