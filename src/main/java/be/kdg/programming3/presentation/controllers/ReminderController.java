package be.kdg.programming3.presentation.controllers;
import be.kdg.programming3.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.kdg.programming3.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class ReminderController {

    private ReminderService reminderService;

    @Autowired
    public ReminderController(ReminderService reminderService) {
        this.reminderService = reminderService;
    }


    @GetMapping("/checkreminder")
    public ResponseEntity<String> checkReminder(){
        if (reminderService.isTimeForReminder()){
            return ResponseEntity.ok("true");
        }else{
            return ResponseEntity.ok("false");
        }
    }


}

