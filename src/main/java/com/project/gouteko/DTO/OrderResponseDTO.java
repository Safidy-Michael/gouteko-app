package com.project.gouteko.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Data
@AllArgsConstructor
public class OrderResponseDTO {
    private UUID id;
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
