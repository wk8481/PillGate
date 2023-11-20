package be.kdg.programming3;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

@SpringBootApplication
@ComponentScan("be.kdg.programming3")
public class PillGateApplication {
    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext context = SpringApplication.run(PillGateApplication.class, args);
 /*       PIRDataHandler dataHandler = context.getBean(PIRDataHandler.class);
        dataHandler.start();*/

    }
}

