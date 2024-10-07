package com.project.gouteko.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderRequestDTO {
    private String firstName;
    private List<ProductOrderDTO> productOrders;

    @Data
    @AllArgsConstructor
    public static class ProductOrderDTO {
        private String productName;
        private int quantity;
        private String unit;
    }
}
