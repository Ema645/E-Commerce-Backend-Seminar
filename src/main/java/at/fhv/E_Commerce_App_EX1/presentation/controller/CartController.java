package at.fhv.E_Commerce_App_EX1.presentation.controller;

import at.fhv.E_Commerce_App_EX1.application.dto.AddToCartRequest;
import at.fhv.E_Commerce_App_EX1.application.dto.CartResponse;
import at.fhv.E_Commerce_App_EX1.application.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
@Tag(name = "Cart", description = "Shopping cart endpoints")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get the cart for a user")
    public ResponseEntity<CartResponse> getCart(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCartByUserId(userId));
    }

    @PostMapping("/{userId}/add")
    @Operation(summary = "Add a product to the user's cart")
    public ResponseEntity<CartResponse> addProduct(
            @PathVariable Long userId,
            @RequestBody AddToCartRequest request) {
        return ResponseEntity.ok(cartService.addProductToCart(userId, request));
    }

    @PostMapping("/{userId}/remove")
    @Operation(summary = "Remove a product from the user's cart")
    public ResponseEntity<CartResponse> removeProduct(
            @PathVariable Long userId,
            @RequestParam Long productId) {
        return ResponseEntity.ok(cartService.removeProductFromCart(userId, productId));
    }

    @PutMapping("/{userId}/update")
    @Operation(summary = "Update quantity of a product in the cart")
    public ResponseEntity<CartResponse> updateQuantity(
            @PathVariable Long userId,
            @RequestParam Long productId,
            @RequestParam int quantity) {
        return ResponseEntity.ok(cartService.updateProductQuantity(userId, productId, quantity));
    }

    @DeleteMapping("/{userId}/clear")
    @Operation(summary = "Clear all items from the user's cart")
    public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}
