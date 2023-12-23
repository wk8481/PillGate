package be.kdg.programming3.pillgate.pres.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthController {

    @GetMapping("/api/check-auth")
    public String checkAuth(HttpSession session) {
        Boolean isLoggedIn = (Boolean) session.getAttribute("isLoggedIn");

        if(isLoggedIn != null && isLoggedIn) {
            return "User is logged in";
        } else {
            return "User is not logged in";
        }
    }
}
