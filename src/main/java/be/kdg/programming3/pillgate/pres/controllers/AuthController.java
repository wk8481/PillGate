package be.kdg.programming3.pillgate.pres.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The {@code AuthController} class is a Spring MVC controller that handles authentication-related requests.
 * It is annotated with {@code @RestController} to indicate that the return values of its methods
 * should be directly written to the response body.
 */
@RestController
public class AuthController {

    /**
     * Handles HTTP GET requests to check the authentication status.
     *
     * @param session The HttpSession object to retrieve user authentication information.
     * @return A message indicating whether the user is logged in or not.
     */
    @GetMapping("/api/check-auth")
    public String checkAuth(HttpSession session) {
        Boolean isLoggedIn = (Boolean) session.getAttribute("isLoggedIn");

        if (isLoggedIn != null && isLoggedIn) {
            return "User is logged in";
        } else {
            return "User is not logged in";
        }
    }
}

