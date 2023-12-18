package be.kdg.programming3.pillgate.pres.controllers.viewmodels;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

import org.springframework.stereotype.Component;

@Component
public class CustomerRegistrationDto {

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
    private String password;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "hasCareGiver cannot be null")
    private Boolean hasCareGiver;

    public CustomerRegistrationDto() {
    }

    public CustomerRegistrationDto(String firstName, String lastName, String username, LocalDate birthDate, String password, String email, boolean hasCareGiver) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.birthDate = birthDate;
        this.password = password;
        this.email = email;
        this.hasCareGiver = hasCareGiver;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getHasCareGiver() {
        return hasCareGiver;
    }

    public void setHasCareGiver(boolean hasCareGiver) {
        this.hasCareGiver = hasCareGiver;
    }
}
