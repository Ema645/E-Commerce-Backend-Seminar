package at.fhv.E_Commerce_App_EX1.infrastructure.persistence;

import at.fhv.E_Commerce_App_EX1.domain.model.User;
import at.fhv.E_Commerce_App_EX1.domain.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long>, UserRepository {
    @Override
    Optional<User> findByUsername(String username);

    @Override
    boolean existsByUsername(String username);

    @Override
    boolean existsByEmail(String email);
}
