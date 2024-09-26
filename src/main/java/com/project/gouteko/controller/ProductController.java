package com.project.gouteko.controller;

import com.project.gouteko.model.Product;
import com.project.gouteko.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{productName}")
    public Product getProductName(@PathVariable String productName){
        return productService.getProductByName(productName);
    }

    @PutMapping("/crupdate/{id}")
    public ResponseEntity<Product> crupdateProduct(@PathVariable UUID id, @RequestBody Product product){
        try {
            productService.createOrUpdateProduct(id,product);
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        catch (RuntimeException e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/id")
    public ResponseEntity<Void> deletProduct(@PathVariable UUID id){
        try {
            productService.deleteProduct(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
