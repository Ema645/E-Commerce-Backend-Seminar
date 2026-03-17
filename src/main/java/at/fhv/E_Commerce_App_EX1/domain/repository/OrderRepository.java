package at.fhv.E_Commerce_App_EX1.domain.repository;

import at.fhv.E_Commerce_App_EX1.domain.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(Long id);
    List<Order> findByUserIdOrderByCreatedAtDesc(Long userId);
}
