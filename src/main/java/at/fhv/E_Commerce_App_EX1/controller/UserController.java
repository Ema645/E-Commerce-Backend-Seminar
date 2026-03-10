package at.fhv.E_Commerce_App_EX1.controller;

import at.fhv.E_Commerce_App_EX1.dto.UserDto;
import at.fhv.E_Commerce_App_EX1.entity.User;
import at.fhv.E_Commerce_App_EX1.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody CreateUserRequest request) {
        User createdUser = userService.createUser(request.firstName(), request.lastName());
        return ResponseEntity.status(HttpStatus.CREATED).body(UserDto.fromEntity(createdUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(UserDto.fromEntity(user));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, String>> handleNoSuchElementException(NoSuchElementException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", exception.getMessage()));
    }

    public record CreateUserRequest(String firstName, String lastName) {
    }
}
