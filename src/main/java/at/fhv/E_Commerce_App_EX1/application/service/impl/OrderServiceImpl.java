package at.fhv.E_Commerce_App_EX1.application.service.impl;

import at.fhv.E_Commerce_App_EX1.application.dto.OrderResponse;
import at.fhv.E_Commerce_App_EX1.application.mapper.OrderMapper;
import at.fhv.E_Commerce_App_EX1.application.service.OrderService;
import at.fhv.E_Commerce_App_EX1.domain.model.*;
import at.fhv.E_Commerce_App_EX1.domain.repository.CartRepository;
import at.fhv.E_Commerce_App_EX1.domain.repository.OrderRepository;
import at.fhv.E_Commerce_App_EX1.domain.repository.ProductRepository;
import at.fhv.E_Commerce_App_EX1.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, CartRepository cartRepository,
                            ProductRepository productRepository, UserRepository userRepository,
                            OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    @Transactional
    public OrderResponse placeOrder(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found with id: " + userId);
        }

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("No cart found for user with id: " + userId));

        if (cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Cannot place an order with an empty cart.");
        }

        // Verify stock availability for all items
        for (CartItem cartItem : cart.getItems()) {
            Product product = productRepository.findById(cartItem.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + cartItem.getProductId()));
            if (product.getStock() < cartItem.getQuantity()) {
                throw new IllegalArgumentException("Not enough stock for product '" + product.getName()
                        + "'. Available: " + product.getStock() + ", requested: " + cartItem.getQuantity());
            }
        }

        // Calculate total
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CartItem cartItem : cart.getItems()) {
            totalPrice = totalPrice.add(
                    cartItem.getProductPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()))
            );
        }

        // Create order
        Order order = new Order(userId, totalPrice);

        // Create order items and deduct stock
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem(
                    order,
                    cartItem.getProductId(),
                    cartItem.getProductName(),
                    cartItem.getProductPrice(),
                    cartItem.getQuantity()
            );
            order.getItems().add(orderItem);

            // Deduct stock
            Product product = productRepository.findById(cartItem.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found."));
            product.setStock(product.getStock() - cartItem.getQuantity());
            productRepository.save(product);
        }

        Order savedOrder = orderRepository.save(order);

        // Clear the cart
        cart.getItems().clear();
        cartRepository.save(cart);

        return orderMapper.toResponse(savedOrder);
    }

    @Override
    public List<OrderResponse> getOrdersByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found with id: " + userId);
        }
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(orderMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + orderId));
        return orderMapper.toResponse(order);
    }
}
