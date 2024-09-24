package com.project.gouteko.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderResponseDTO {
    private String userName;
    private String address;
    private List<ProductOrderDTO> products;
    private BigDecimal totalAmount;
    private LocalDateTime orderDate;

    @Data
    @AllArgsConstructor
    public static class ProductOrderDTO {
        private String productName;
        private int quantity;
        private String unit;
        private BigDecimal unitPrice;
    }
}
