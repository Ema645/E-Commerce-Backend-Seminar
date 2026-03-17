package at.fhv.E_Commerce_App_EX1.application.service;

import at.fhv.E_Commerce_App_EX1.application.dto.CreateUserRequest;
import at.fhv.E_Commerce_App_EX1.application.dto.UpdateUserRequest;
import at.fhv.E_Commerce_App_EX1.application.dto.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(CreateUserRequest request);
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Long id);
    UserResponse updateUser(Long id, UpdateUserRequest request);
    void deleteUser(Long id);
}
