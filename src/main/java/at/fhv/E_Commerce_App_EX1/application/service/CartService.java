package at.fhv.E_Commerce_App_EX1.application.service;

import at.fhv.E_Commerce_App_EX1.application.dto.AddToCartRequest;
import at.fhv.E_Commerce_App_EX1.application.dto.CartResponse;

public interface CartService {
    CartResponse getCartByUserId(Long userId);
    CartResponse addProductToCart(Long userId, AddToCartRequest request);
    CartResponse removeProductFromCart(Long userId, Long productId);
    CartResponse updateProductQuantity(Long userId, Long productId, int quantity);
    void clearCart(Long userId);
}
