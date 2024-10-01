package com.project.gouteko.controller;

import com.project.gouteko.model.Product;
import com.project.gouteko.model.User;
import com.project.gouteko.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/")
    public List<Product> getAll(){
        return productService.findAll();
    }
    @GetMapping("/{productName}")
    public Product getProductName(@PathVariable String productName){
        return productService.getProductByName(productName);
    }
    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product newProduct = productService.create(product);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable UUID id, @RequestBody Product product){
        try {
            productService.updateProduct(id,product);
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        catch (RuntimeException e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
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
