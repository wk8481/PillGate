package be.kdg.programming3.pillgate.pres.controllers.viewmodels;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

import org.springframework.stereotype.Component;

/**
 * Data Transfer Object (DTO) for customer registration information.
 * This class represents the data required for customer registration.
 *
 * @author Team PillGate
 */
@Component
public class CustomerRegistrationDto {

    /**
     * First name of the customer.
     */
    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    /**
     * Last name of the customer.
     */
    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    /**
     * Username chosen by the customer.
     * Must be between 3 and 20 characters.
     */
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    /**
     * Date of birth of the customer.
     * Must be in the past.
     */
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    /**
     * Password for customer authentication.
     * Must be between 6 and 20 characters.
     */
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
    private String password;

    /**
     * Email address of the customer.
     */
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    /**
     * Flag indicating whether the customer has a caregiver.
     */
    @NotNull(message = "hasCareGiver cannot be null")
    private boolean hasCareGiver;

    /**
     * Default constructor.
     */
    public CustomerRegistrationDto() {
    }

    /**
     * Parameterized constructor to initialize customer registration information.
     *
     * @param firstName     The first name of the customer.
     * @param lastName      The last name of the customer.
     * @param username      The chosen username for authentication.
     * @param birthDate     The date of birth of the customer.
     * @param password      The password for customer authentication.
     * @param email         The email address of the customer.
     * @param hasCareGiver  Flag indicating whether the customer has a caregiver.
     */
    public CustomerRegistrationDto(String firstName, String lastName, String username, LocalDate birthDate, String password, String email, boolean hasCareGiver) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.birthDate = birthDate;
        this.password = password;
        this.email = email;
        this.hasCareGiver = hasCareGiver;
    }

    /**
     * Get the first name.
     *
     * @return The first name of the customer.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set the first name.
     *
     * @param firstName The first name of the customer.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Get the last name.
     *
     * @return The last name of the customer.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set the last name.
     *
     * @param lastName The last name of the customer.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get the date of birth.
     *
     * @return The date of birth of the customer.
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * Set the date of birth.
     *
     * @param birthDate The date of birth of the customer.
     */
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Get the username.
     *
     * @return The chosen username for authentication.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username.
     *
     * @param username The chosen username for authentication.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the password.
     *
     * @return The password for customer authentication.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password.
     *
     * @param password The password for customer authentication.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the email address.
     *
     * @return The email address of the customer.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the email address.
     *
     * @param email The email address of the customer.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Check if the customer has a caregiver.
     *
     * @return True if the customer has a caregiver, false otherwise.
     */
    public boolean getHasCareGiver() {
        return hasCareGiver;
    }

    /**
     * Set the caregiver flag.
     *
     * @param hasCareGiver Flag indicating whether the customer has a caregiver.
     */
    public void setHasCareGiver(boolean hasCareGiver) {
        this.hasCareGiver = hasCareGiver;
    }

    /**
     * Override toString() to provide a string representation of the object.
     *
     * @return A string representation of the CustomerRegistrationDto object.
     */
    @Override
    public String toString() {
        return "CustomerRegistrationDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", birthDate=" + birthDate +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", hasCareGiver=" + hasCareGiver +
                '}';
    }
}
