//package be.kdg.programming3.oldproj.controllers;
//import be.kdg.programming3.oldproj.service.ReminderService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import be.kdg.programming3.oldproj.repository.UserRepository;
//
//@RestController
//@RequestMapping("/api")
//public class ReminderController {
//
//
//    private ReminderService reminderService;
//    private Logger logger = LoggerFactory.getLogger(ReminderController.class);
//
//    @Autowired
//    public ReminderController(ReminderService reminderService) {
//        this.reminderService = reminderService;
//    }
//
//
//    @GetMapping("/checkreminder")
//    public ResponseEntity<String> checkReminder(){
//        logger.info("Checking if it is time for a reminder");
//        if (reminderService.isTimeForReminder()){
//            return ResponseEntity.ok("true");
//        }else{
//            return ResponseEntity.ok("false");
//        }
//    }
//
//
//}
//
