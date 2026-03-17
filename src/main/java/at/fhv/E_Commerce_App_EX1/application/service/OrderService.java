package at.fhv.E_Commerce_App_EX1.application.service;

import at.fhv.E_Commerce_App_EX1.application.dto.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse placeOrder(Long userId);
    List<OrderResponse> getOrdersByUserId(Long userId);
    OrderResponse getOrderById(Long orderId);
}
