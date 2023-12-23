package be.kdg.programming3.pillgate.pres.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The {@code HomeController} class is a Spring MVC controller responsible for handling requests related to the home page.
 * It maps to the "/home" endpoint.
 */
@RequestMapping("/home")
@Controller
public class HomeController {

    /**
     * Handles HTTP GET requests for the home page.
     * @return The logical view name "home" to be resolved by the view resolver.
     */
    @GetMapping
    public String showHomeView() {
        return "home";
    }
}

