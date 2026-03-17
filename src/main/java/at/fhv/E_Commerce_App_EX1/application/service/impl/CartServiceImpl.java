package at.fhv.E_Commerce_App_EX1.application.service.impl;

import at.fhv.E_Commerce_App_EX1.application.dto.AddToCartRequest;
import at.fhv.E_Commerce_App_EX1.application.dto.CartResponse;
import at.fhv.E_Commerce_App_EX1.application.mapper.CartMapper;
import at.fhv.E_Commerce_App_EX1.application.service.CartService;
import at.fhv.E_Commerce_App_EX1.domain.model.Cart;
import at.fhv.E_Commerce_App_EX1.domain.model.CartItem;
import at.fhv.E_Commerce_App_EX1.domain.model.Product;
import at.fhv.E_Commerce_App_EX1.domain.repository.CartRepository;
import at.fhv.E_Commerce_App_EX1.domain.repository.ProductRepository;
import at.fhv.E_Commerce_App_EX1.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartMapper cartMapper;

    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository,
                           UserRepository userRepository, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.cartMapper = cartMapper;
    }

    @Override
    public CartResponse getCartByUserId(Long userId) {
        validateUserExists(userId);
        Cart cart = getOrCreateCart(userId);
        return cartMapper.toResponse(cart);
    }

    @Override
    @Transactional
    public CartResponse addProductToCart(Long userId, AddToCartRequest request) {
        validateUserExists(userId);
        if (request.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + request.getProductId()));

        if (product.getStock() < request.getQuantity()) {
            throw new IllegalArgumentException("Not enough stock for product '" + product.getName()
                    + "'. Available: " + product.getStock() + ", requested: " + request.getQuantity());
        }

        Cart cart = getOrCreateCart(userId);

        CartItem existingItem = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(request.getProductId()))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            int newQuantity = existingItem.getQuantity() + request.getQuantity();
            if (product.getStock() < newQuantity) {
                throw new IllegalArgumentException("Not enough stock for product '" + product.getName()
                        + "'. Available: " + product.getStock() + ", already in cart: "
                        + existingItem.getQuantity() + ", requested to add: " + request.getQuantity());
            }
            existingItem.setQuantity(newQuantity);
        } else {
            CartItem newItem = new CartItem(cart, product.getId(), product.getName(), product.getPrice(), request.getQuantity());
            cart.getItems().add(newItem);
        }

        Cart saved = cartRepository.save(cart);
        return cartMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public CartResponse removeProductFromCart(Long userId, Long productId) {
        validateUserExists(userId);
        Cart cart = getOrCreateCart(userId);

        boolean removed = cart.getItems().removeIf(item -> item.getProductId().equals(productId));
        if (!removed) {
            throw new IllegalArgumentException("Product with id " + productId + " is not in the cart.");
        }

        Cart saved = cartRepository.save(cart);
        return cartMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public CartResponse updateProductQuantity(Long userId, Long productId, int quantity) {
        validateUserExists(userId);
        if (quantity <= 0) {
            return removeProductFromCart(userId, productId);
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));
        if (product.getStock() < quantity) {
            throw new IllegalArgumentException("Not enough stock for product '" + product.getName()
                    + "'. Available: " + product.getStock() + ", requested: " + quantity);
        }

        Cart cart = getOrCreateCart(userId);
        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product with id " + productId + " is not in the cart."));

        item.setQuantity(quantity);
        Cart saved = cartRepository.save(cart);
        return cartMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void clearCart(Long userId) {
        validateUserExists(userId);
        Cart cart = getOrCreateCart(userId);
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    private Cart getOrCreateCart(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart(userId);
                    return cartRepository.save(newCart);
                });
    }

    private void validateUserExists(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found with id: " + userId);
        }
    }
}
