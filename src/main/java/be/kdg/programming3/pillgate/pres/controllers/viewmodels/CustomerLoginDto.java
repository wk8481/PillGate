package be.kdg.programming3.pillgate.pres.controllers.viewmodels;

import org.springframework.stereotype.Component;

@Component
public class CustomerLoginDto {
    private String password;
    private String email;

    public CustomerLoginDto() {
    }

    public CustomerLoginDto(String email,String password)  {
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
