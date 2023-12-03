package be.kdg.programming3.pillgate.pres.controllers.viewmodels;

import org.springframework.stereotype.Component;

@Component
public class CustomerLoginDto {
    private String username;
    private String password;

    public CustomerLoginDto() {
    }

    public CustomerLoginDto(String username, String password) {
        this.username = username;
        this.password = password;
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
}
