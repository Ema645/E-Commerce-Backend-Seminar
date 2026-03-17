package at.fhv.E_Commerce_App_EX1.presentation.controller;

import at.fhv.E_Commerce_App_EX1.application.dto.OrderResponse;
import at.fhv.E_Commerce_App_EX1.application.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Orders", description = "Order management endpoints")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/{userId}/place")
    @Operation(summary = "Place an order from the user's cart")
    public ResponseEntity<OrderResponse> placeOrder(@PathVariable Long userId) {
        OrderResponse order = orderService.placeOrder(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get all orders for a user (order history)")
    public ResponseEntity<List<OrderResponse>> getOrdersByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getOrdersByUserId(userId));
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "Get order details by order ID")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }
}
