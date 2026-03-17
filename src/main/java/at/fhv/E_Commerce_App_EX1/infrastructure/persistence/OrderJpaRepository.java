package at.fhv.E_Commerce_App_EX1.infrastructure.persistence;

import at.fhv.E_Commerce_App_EX1.domain.model.Order;
import at.fhv.E_Commerce_App_EX1.domain.repository.OrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderJpaRepository extends JpaRepository<Order, Long>, OrderRepository {
    @Override
    List<Order> findByUserIdOrderByCreatedAtDesc(Long userId);
}
