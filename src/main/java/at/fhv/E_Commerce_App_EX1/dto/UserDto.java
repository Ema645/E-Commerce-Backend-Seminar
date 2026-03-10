package at.fhv.E_Commerce_App_EX1.dto;

import at.fhv.E_Commerce_App_EX1.entity.User;

public record UserDto(Long id, String firstName, String lastName) {

    public static UserDto fromEntity(User user) {
        return new UserDto(user.getId(), user.getFirstName(), user.getLastName());
    }
}

