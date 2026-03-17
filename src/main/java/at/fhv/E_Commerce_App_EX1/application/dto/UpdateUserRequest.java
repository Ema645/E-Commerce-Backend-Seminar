package at.fhv.E_Commerce_App_EX1.application.dto;

public class UpdateUserRequest {
    private String email;
    private String firstName;
    private String lastName;

    public UpdateUserRequest() {}

    public UpdateUserRequest(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
}
