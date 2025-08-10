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
import com.project.gouteko.utils.PaginationRequest;
import com.project.gouteko.utils.PaginationUtils;
import com.project.gouteko.utils.PagingResult;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
        User user = userRepository.findByEmail(orderRequest.getEmail())
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found with email: " + orderRequest.getEmail()));

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());

        List<OrderDetail> orderDetails = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderRequestDTO.ProductOrderDTO productOrder : orderRequest.getProductOrders()) {
            Product product = productRepository.findByName(productOrder.getProductName())
                    .orElseThrow(() -> new RuntimeException("Product not found with name: " + productOrder.getProductName()));

            // Vérification de la disponibilité du stock
            if (product.getAvailableQuantity() < productOrder.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }

            // Mise à jour du stock disponible
            product.setAvailableQuantity(product.getAvailableQuantity() - productOrder.getQuantity());
            productRepository.save(product);

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProduct(product);
            orderDetail.setOrderedQuantity(productOrder.getQuantity());
            orderDetail.setUnit(productOrder.getUnit());

            BigDecimal unitPrice = product.getPrice();
            orderDetail.setUnitPrice(unitPrice);

            totalAmount = totalAmount.add(unitPrice.multiply(BigDecimal.valueOf(productOrder.getQuantity())));

            orderDetails.add(orderDetail);
        }

        order.setTotalAmount(totalAmount);
        orderRepository.save(order);

        for (OrderDetail orderDetail : orderDetails) {
            orderDetail.setOrder(order);
        }

        orderDetailRepository.saveAll(orderDetails);

        List<OrderResponseDTO.ProductOrderDTO> productOrders = orderDetails.stream()
                .map(orderDetail -> new OrderResponseDTO.ProductOrderDTO(
                        orderDetail.getProduct().getName(),
                        orderDetail.getOrderedQuantity(),
                        orderDetail.getUnit(),
                        orderDetail.getUnitPrice()
                ))
                .collect(Collectors.toList());

        return new OrderResponseDTO(
                user.getId(),
                user.getFirstName(),
                user.getAddress(),
                productOrders,
                totalAmount,
                order.getOrderDate()
        );
    }


        public PagingResult<OrderResponseDTO> getAllOrders(PaginationRequest request) {
            Pageable pageable = PaginationUtils.getPageable(request);
            if(request.getSortField() != null) {
                pageable = PageRequest.of(
                        request.getPage(),
                        request.getSize(),
                        Sort.by(Sort.Direction.DESC, "orderDate")
                );
            }

            final Page<Order> orders = orderRepository.findAll(pageable);

            final Page<OrderResponseDTO> ordersDTO = orders.map(this::convertToOrderResponseDTO);

            return new PagingResult<>(ordersDTO);
        }

    // Method to fetch an order by ID
    public Optional<OrderResponseDTO> getOrderById(UUID id) {
        return orderRepository.findById(id).map(this::convertToOrderResponseDTO);
    }

    // Helper method to convert Order to OrderResponseDTO
    private OrderResponseDTO convertToOrderResponseDTO(Order order) {
        List<OrderResponseDTO.ProductOrderDTO> productOrderDTOs = order.getOrderDetails().stream()
                .map(orderDetail -> new OrderResponseDTO.ProductOrderDTO(
                        orderDetail.getProduct().getName(),
                        orderDetail.getOrderedQuantity(),
                        orderDetail.getUnit(),
                        orderDetail.getUnitPrice()
                ))
                .collect(Collectors.toList());

        return new OrderResponseDTO(
                order.getId(),
                order.getUser().getFirstName(),
                order.getUser().getAddress(),
                productOrderDTOs,
                order.getTotalAmount(),
                order.getOrderDate()
        );
    }
    public List<OrderResponseDTO> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        List<Order> orders = orderRepository.findAllByOrderDateBetween(startDate, endDate);
        return orders.stream().map(this::convertToOrderResponseDTO).collect(Collectors.toList());
    }


}

