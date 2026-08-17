package com.soham29640.mycroservices.product_service.controller;

import com.soham29640.mycroservices.product_service.dto.ProductRequest;
import com.soham29640.mycroservices.product_service.dto.ProductResponse;
import com.soham29640.mycroservices.product_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest){
        try{
            ProductResponse productResponse = productService.createProduct(productRequest);
            return new ResponseEntity<>(productResponse,HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/return")
    public ResponseEntity<List<ProductResponse>> returnAllProducts(){
        List<ProductResponse> products = productService.returnAllProducts();
        if (products.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(products,HttpStatus.OK);
    }
}
