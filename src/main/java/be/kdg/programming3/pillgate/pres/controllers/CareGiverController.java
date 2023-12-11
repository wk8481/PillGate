package be.kdg.programming3.pillgate.pres.controllers;

import be.kdg.programming3.pillgate.domain.user.CareGiver;
import be.kdg.programming3.pillgate.service.CareGiverService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class CareGiverController {
        private final CareGiverService careGiverService;

        private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

        public CareGiverController(CareGiverService careGiverService) {
            this.careGiverService = careGiverService;
        }
        @GetMapping("/caregivers")
        public String findAllCaregivers(Model model, HttpSession session) {
            List<CareGiver> careGivers = careGiverService.getCaregivers();
            model.addAttribute("caregivers", careGivers);
            log.info("Caregivers: {} ", careGivers);
            return "caregivers";
        }

        @GetMapping("/caregivers/addCaregiver")
        public String addCaregiver(CareGiver careGiver) {
            log.info("Adding a caregiver: " + careGiver);
            careGiverService.createCareGiver(careGiver);
            return "redirect:caregivers";
        }
}
