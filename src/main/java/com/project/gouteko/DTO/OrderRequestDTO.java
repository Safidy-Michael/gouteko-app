package com.project.gouteko.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderRequestDTO {
    private String firstName; // Prénom de l'utilisateur
    private List<ProductOrderDTO> productOrders; // Liste des produits commandés

    @Data
    @AllArgsConstructor
    public static class ProductOrderDTO {
        private String productName; // Nom du produit
        private int quantity; // Quantité commandée
        private String unit; // Unité du produit (par exemple, "kg", "g", "pcs", etc.)
    }
}
