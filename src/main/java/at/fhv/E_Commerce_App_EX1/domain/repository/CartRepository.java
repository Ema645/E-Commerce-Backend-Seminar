package at.fhv.E_Commerce_App_EX1.domain.repository;

import at.fhv.E_Commerce_App_EX1.domain.model.Cart;

import java.util.Optional;

public interface CartRepository {
    Cart save(Cart cart);
    Optional<Cart> findById(Long id);
    Optional<Cart> findByUserId(Long userId);
}
