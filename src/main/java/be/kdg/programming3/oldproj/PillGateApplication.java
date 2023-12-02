package be.kdg.programming3.oldproj;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

@SpringBootApplication
@ComponentScan(basePackages = "be.kdg.programming3.oldproj.repository")
public class PillGateApplication {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(PillGateApplication.class, args);
 /*       PIRDataHandler dataHandler = context.getBean(PIRDataHandler.class);
        dataHandler.start();*/

    }
}

