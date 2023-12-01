package be.kdg.programming3.pillgate.pres.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/teddy")
@Controller
public class HomeController {

    @GetMapping
    public String showHomeView() {
        return "home";
    }
}

