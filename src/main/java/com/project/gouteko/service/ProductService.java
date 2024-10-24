package com.project.gouteko.service;

import com.project.gouteko.DTO.ProductDTO;
import com.project.gouteko.model.Product;
import com.project.gouteko.repository.ProductRepository;
import lombok.AllArgsConstructor;
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
        Product product = toDomain(productDTO, imageFile);
        return  productRepository.save(product);
    }


    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
