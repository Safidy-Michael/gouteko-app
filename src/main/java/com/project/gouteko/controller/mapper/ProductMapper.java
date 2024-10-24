package com.project.gouteko.controller.mapper;

import com.project.gouteko.DTO.ProductDTO;
import com.project.gouteko.model.Product;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

@Component
public class ProductMapper {

    public static Product toDomain(ProductDTO productDTO, MultipartFile imageFile) throws Exception {
        Product domainProduct = new Product();
        domainProduct.setId((productDTO.getId()));
        domainProduct.setName(productDTO.getName());
        domainProduct.setDescription(productDTO.getDescription());
        domainProduct.setPrice(productDTO.getPrice());
        domainProduct.setAvailableQuantity(productDTO.getAvailableQuantity());
        domainProduct.setCategory(productDTO.getCategory());

        if (imageFile != null && !imageFile.isEmpty()) {
            byte[] imageBytes = imageFile.getBytes();
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            domainProduct.setProductImage("data:image/jpeg;base64," + base64Image);
        } else {
            domainProduct.setProductImage(productDTO.getImageBase64());
        }

        return domainProduct;
    }

    public static ProductDTO toView(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setAvailableQuantity(product.getAvailableQuantity());
        productDTO.setCategory(product.getCategory());
        productDTO.setImageBase64(product.getProductImage());

        return productDTO;
    }
}
