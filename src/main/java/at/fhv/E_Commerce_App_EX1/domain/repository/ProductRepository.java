package at.fhv.E_Commerce_App_EX1.domain.repository;

import at.fhv.E_Commerce_App_EX1.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(Long id);
    List<Product> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
}
