package at.fhv.E_Commerce_App_EX1.application.mapper;

import at.fhv.E_Commerce_App_EX1.application.dto.CartItemResponse;
import at.fhv.E_Commerce_App_EX1.application.dto.CartResponse;
import at.fhv.E_Commerce_App_EX1.domain.model.Cart;
import at.fhv.E_Commerce_App_EX1.domain.model.CartItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartMapper {

    public CartResponse toResponse(Cart cart) {
        List<CartItemResponse> itemResponses = cart.getItems().stream()
                .map(this::toItemResponse)
                .collect(Collectors.toList());

        return new CartResponse(
                cart.getId(),
                cart.getUserId(),
                itemResponses,
                cart.getItemsCount(),
                cart.getTotal(),
                cart.getCreatedAt(),
                cart.getUpdatedAt()
        );
    }

    private CartItemResponse toItemResponse(CartItem item) {
        return new CartItemResponse(
                item.getId(),
                item.getProductId(),
                item.getProductName(),
                item.getProductPrice(),
                item.getQuantity()
        );
    }
}
