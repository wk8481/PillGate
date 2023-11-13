package be.kdg.programming3.presentation.controllers;

import be.kdg.programming3.domain.pillbox.PillBox;
import be.kdg.programming3.service.PillBoxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pillbox")
public class PillBoxController {

    private static final Logger logger = LoggerFactory.getLogger(PillBoxController.class);

    private final PillBoxService pillBoxService;


    public PillBoxController(PillBoxService pillBoxService) {
        this.pillBoxService = pillBoxService;
    }

    public String createPillBox(PillBox pillBox) {
        logger.debug("Adding a pillbox: " + pillBox);
        pillBoxService.getPillBox();
        return "redirect:pillbox";
    }
}
