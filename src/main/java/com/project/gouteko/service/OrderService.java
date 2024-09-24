package com.project.gouteko.service;

import com.project.gouteko.DTO.OrderRequestDTO;
import com.project.gouteko.DTO.OrderResponseDTO;
import com.project.gouteko.model.Order;
import com.project.gouteko.model.OrderDetail;
import com.project.gouteko.model.Product;
import com.project.gouteko.model.User;
import com.project.gouteko.repository.OrderDetailRepository;
import com.project.gouteko.repository.OrderRepository;
import com.project.gouteko.repository.ProductRepository;
import com.project.gouteko.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {
    @Autowired
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public OrderResponseDTO placeOrder(OrderRequestDTO orderRequest) {
        // Vérifier si l'utilisateur existe en fonction de son prénom
        User user = userRepository.findByFirstName(orderRequest.getFirstName())
                .orElseThrow(() -> new RuntimeException("User not found with first name: " + orderRequest.getFirstName()));

        // Créer une nouvelle commande pour l'utilisateur
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now()); // Définir la date de la commande

        List<OrderDetail> orderDetails = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO; // Initialiser le montant total

        // Parcourir les produits commandés et créer les détails de commande
        for (OrderRequestDTO.ProductOrderDTO productOrder : orderRequest.getProductOrders()) {
            Product product = productRepository.findByName(productOrder.getProductName())
                    .orElseThrow(() -> new RuntimeException("Product not found with name: " + productOrder.getProductName()));

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProduct(product);
            orderDetail.setOrderedQuantity(productOrder.getQuantity());
            orderDetail.setUnit(productOrder.getUnit());

            // Calculer le prix unitaire et le montant total pour ce produit
            BigDecimal unitPrice = product.getPrice();
            orderDetail.setUnitPrice(unitPrice);

            // Ajouter au montant total de la commande
            totalAmount = totalAmount.add(unitPrice.multiply(BigDecimal.valueOf(productOrder.getQuantity())));

            orderDetails.add(orderDetail);
        }

        // Sauvegarder la commande d'abord pour obtenir son ID
        order.setTotalAmount(totalAmount); // Assigner le montant total à la commande
        orderRepository.save(order); // Enregistrer la commande

        // Maintenant, associer les détails de commande à la commande persistée
        for (OrderDetail orderDetail : orderDetails) {
            orderDetail.setOrder(order); // Définir la commande pour chaque détail
        }

        // Sauvegarder les détails de la commande
        orderDetailRepository.saveAll(orderDetails);

        // Construire la réponse OrderResponseDTO
        List<OrderResponseDTO.ProductOrderDTO> productOrders = orderDetails.stream()
                .map(orderDetail -> new OrderResponseDTO.ProductOrderDTO(
                        orderDetail.getProduct().getName(),
                        orderDetail.getOrderedQuantity(),
                        orderDetail.getUnit(),
                        orderDetail.getUnitPrice()
                ))
                .collect(Collectors.toList());

        // Créer et retourner l'objet OrderResponseDTO
        return new OrderResponseDTO(
                user.getFirstName(),
                user.getAddress(),
                productOrders,
                totalAmount,
                order.getOrderDate()
        );
    }
}
