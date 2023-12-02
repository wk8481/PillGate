package be.kdg.programming3.pillgate.pres.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class PresentedByController {
    @GetMapping("/PresentedBy")
    public String presentedBy() {
        return "PresentedBy";
    }

}
