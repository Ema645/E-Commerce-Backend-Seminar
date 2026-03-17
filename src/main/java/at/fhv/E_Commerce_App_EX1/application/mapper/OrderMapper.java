package at.fhv.E_Commerce_App_EX1.application.mapper;

import at.fhv.E_Commerce_App_EX1.application.dto.OrderItemResponse;
import at.fhv.E_Commerce_App_EX1.application.dto.OrderResponse;
import at.fhv.E_Commerce_App_EX1.domain.model.Order;
import at.fhv.E_Commerce_App_EX1.domain.model.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderResponse toResponse(Order order) {
        List<OrderItemResponse> itemResponses = order.getItems().stream()
                .map(this::toItemResponse)
                .collect(Collectors.toList());

        return new OrderResponse(
                order.getId(),
                order.getUserId(),
                itemResponses,
                order.getTotalPrice(),
                order.getStatus().name(),
                order.getCreatedAt()
        );
    }

    private OrderItemResponse toItemResponse(OrderItem item) {
        return new OrderItemResponse(
                item.getId(),
                item.getProductId(),
                item.getProductName(),
                item.getProductPrice(),
                item.getQuantity(),
                item.getSubtotal()
        );
    }
}
