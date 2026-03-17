package at.fhv.E_Commerce_App_EX1.infrastructure.persistence;

import at.fhv.E_Commerce_App_EX1.domain.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemJpaRepository extends JpaRepository<CartItem, Long> {
}
