package be.kdg.programming3.pillgate.pres.controllers.viewmodels;//package be.kdg.programming3.oldproj.controllers.viewmodels;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CustomerRegistrationDto {

    private String firstName;
    private String lastName;
    private String username;
    private LocalDate birthDate;
    private String password;
    private String email;
    private boolean hasCareGiver;

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
