package com.project.gouteko.controller;


import com.project.gouteko.DTO.ProductDTO;
import com.project.gouteko.controller.mapper.ProductMapper;
import com.project.gouteko.model.Product;
import com.project.gouteko.service.ProductService;
import com.project.gouteko.utils.PageableUtils;
import com.project.gouteko.utils.PaginationRequest;
import com.project.gouteko.utils.PagingResult;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {

    @Autowired
    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping("/")
    public PagingResult<ProductDTO> getAll(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) Sort.Direction direction
    ) {
        final PaginationRequest request = new PaginationRequest(page, size, sortField, direction);

        final PagingResult<ProductDTO> products = productService.findAll(request);
        return ResponseEntity.ok(products).getBody();
    }

    @GetMapping("/{productName}")
    public Product getProductName(@PathVariable String productName) {
        return productService.getProductByName(productName);
    }

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(
            @ModelAttribute ProductDTO productDTO,
            @RequestParam("productImage") MultipartFile imageFile) {
        try {
            Product createProduct = productService.createProduct(productDTO, imageFile);
            return new ResponseEntity<>(createProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable UUID id,
            @ModelAttribute ProductDTO productDTO,
            @RequestParam(value = "productImage", required = false) MultipartFile productImage) {
        try {
            Product updatedProduct = productService.updateProduct(id, productDTO, productImage);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        try {
            productService.deleteProduct(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/filter")
    public Page<Product> getProductsByCategory(
            @RequestParam String category,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ){
        Pageable pageable = PageableUtils.createPageable(page, size);
        Page<Product> productPage = productService.findProductByCategory(category, pageable);
        return productPage;
    }
}
