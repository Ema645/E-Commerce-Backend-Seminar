package at.fhv.E_Commerce_App_EX1.infrastructure.persistence;

import at.fhv.E_Commerce_App_EX1.domain.model.Cart;
import at.fhv.E_Commerce_App_EX1.domain.repository.CartRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartJpaRepository extends JpaRepository<Cart, Long>, CartRepository {
    @Override
    Optional<Cart> findByUserId(Long userId);
}
