package be.kdg.programming3.pillgate.pres.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * The {@code PresentedByController} class is a Spring MVC controller that handles requests related to the "Presented By" page.
 * It is responsible for displaying information about the entity or individuals presenting the application.
 * @author PillGate
 */
@Controller
public class PresentedByController {

    /**
     * Handles HTTP GET requests to display the "Presented By" page.
     * @return The view name for the "Presented By" page.
     */
    @GetMapping("/PresentedBy")
    public String presentedBy() {
        return "presentedBy";
    }

}
