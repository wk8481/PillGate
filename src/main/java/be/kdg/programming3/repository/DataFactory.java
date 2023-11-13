package be.kdg.programming3.repository;

import be.kdg.programming3.domain.pillbox.Medicine;
import be.kdg.programming3.domain.pillbox.PillBox;
import be.kdg.programming3.domain.sensor.WeightSensor;
import be.kdg.programming3.domain.user.CareGiver;
import be.kdg.programming3.domain.user.Customer;
import be.kdg.programming3.domain.user.Dashboard;
import be.kdg.programming3.domain.user.MedicationSchedule;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.CommandLineRunner;

import java.util.ArrayList;
import java.util.List;

public class DataFactory implements CommandLineRunner {

    private final PillBoxRepository pillBoxRepository;
    private final SensorRepository sensorRepository;
    private final UserRepository userRepository;

    public DataFactory(PillBoxRepository pillBoxRepository, SensorRepository sensorRepository, UserRepository userRepository) {
        this.pillBoxRepository = pillBoxRepository;
        this.sensorRepository = sensorRepository;
        this.userRepository = userRepository;
    }
    @PostConstruct
    public void seed() {
        List<Medicine> medicines = new ArrayList<>();
        List<WeightSensor> weightSensors = new ArrayList<>();
        List<PillBox> pillBoxes = new ArrayList<>();
        List<Customer> customers = new ArrayList<>();
        List<Dashboard> dashboards = new ArrayList<>();
        List<MedicationSchedule> medicationSchedules = new ArrayList<>();
        List<CareGiver> careGivers = new ArrayList<>();

    }

    // Clear repositories
/*    PillBoxRepository.readPillBoxes().clear();
    PillBoxRepository.readMedicines().clear();*/ // to implement later

    // Create Medicines


    // Create PillBoxes

    // Create WeightSensors

    // WeightSensor weightSensor1 = new WeightSensor()  to implement later

    // Create Customers

    @Override
    public void run(String... args) throws Exception {

    }
}
