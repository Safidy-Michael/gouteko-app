package com.project.gouteko.service;

import com.project.gouteko.DTO.ProductDTO;
import com.project.gouteko.model.Product;
import com.project.gouteko.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.project.gouteko.controller.mapper.ProductMapper.toDomain;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product getProductByName(String productName){
        Optional<Product> productN =  productRepository.findByName(productName);
        if(productN.isPresent()){
            Product product =  productN.get();
            return  product;
        }
        else throw new RuntimeException("Product not found with id: \" "+ productName );
    }

    public Product updateProduct(UUID id,ProductDTO productDTO, MultipartFile imageFile)throws Exception {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Product updateProduct = toDomain(productDTO, imageFile);
        updateProduct.setId(existingProduct.getId());
        return productRepository.save(updateProduct);

    }
    public void deleteProduct(UUID id){
        Optional<Product> productId = productRepository.findById(id);
        if(!productId.isPresent()){
            throw new RuntimeException("Product not found with id: " + id );
        }
        else {
            productRepository.deleteById(id);
        }
    }
    public Product createProduct(ProductDTO productDTO, MultipartFile imageFile) throws Exception {
        boolean exists = productRepository.existsByName(productDTO.getName());
        if (exists) {
            throw new RuntimeException("Un produit avec le nom '" + productDTO.getName() + "' existe déjà.");
        }
        Product product = toDomain(productDTO, imageFile);
        return  productRepository.save(product);
    }


    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public  Page<Product> findProductByCategory(String category, Pageable pageable){
        return  productRepository.findByCategory(category, pageable);
    }
}
