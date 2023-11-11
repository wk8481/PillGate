package be.kdg.programming3;

import be.kdg.programming3.repository.PIRDataHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class PillGateApplication {
    public static void main(String[] args) {
         SpringApplication.run(PillGateApplication.class, args);
        //PIRDataHandler dataHandler = context.getBean(PIRDataHandler.class);
    }
}

