package be.kdg.programming3.pillgate.pres.controllers.viewmodels;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import org.springframework.stereotype.Component;

/**
 * Data Transfer Object (DTO) for customer login information.
 * This class represents the data required for customer login.
 * @author Team PillGate
 */
@Component
public class CustomerLoginDto {

    /**
     * Email address of the customer.
     */
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    /**
     * Password for customer authentication.
     * Must be between 6 and 20 characters.
     */
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
    private String password;

    /**
     * Default constructor.
     */
    public CustomerLoginDto() {
    }

    /**
     * Parameterized constructor to initialize email and password.
     * @param email    The email address of the customer.
     * @param password The password for customer authentication.
     */
    public CustomerLoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "CustomerLoginDto{" +
                "password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
