package be.kdg.programming3.pillgate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "be.kdg.programming3.pillgate")
public class PillGateApplication {

    public static void main(String[] args) {
        SpringApplication.run(PillGateApplication.class, args);
    }

}
