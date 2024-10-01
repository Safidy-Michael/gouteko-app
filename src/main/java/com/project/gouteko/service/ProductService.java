package com.project.gouteko.service;

import com.project.gouteko.model.Product;
import com.project.gouteko.model.User;
import com.project.gouteko.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public Product updateProduct(UUID id,Product crupdateProduct){
        Optional<Product> existingProduct = productRepository.findById(id);

        if(existingProduct.isPresent()){
            Product product = existingProduct.get();
            product.setName(crupdateProduct.getName());
            product.setPrice(crupdateProduct.getPrice());
            product.setDescription(crupdateProduct.getDescription());
            product.setAvailableQuantity(crupdateProduct.getAvailableQuantity());
            product.setCategory(crupdateProduct.getCategory());

            return productRepository.save(product);
        }
        else throw new RuntimeException("Product id not found" + id);
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
    public Product create(Product product){
        return  productRepository.save(product);
    }


    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
