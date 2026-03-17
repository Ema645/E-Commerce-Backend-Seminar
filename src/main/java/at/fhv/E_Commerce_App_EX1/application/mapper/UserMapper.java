package at.fhv.E_Commerce_App_EX1.application.mapper;

import at.fhv.E_Commerce_App_EX1.application.dto.CreateUserRequest;
import at.fhv.E_Commerce_App_EX1.application.dto.UserResponse;
import at.fhv.E_Commerce_App_EX1.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getCreatedAt()
        );
    }

    public User toEntity(CreateUserRequest request) {
        return new User(
                request.getUsername(),
                request.getEmail(),
                request.getFirstName(),
                request.getLastName()
        );
    }
}
