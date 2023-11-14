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

        //clear repositories
        pillBoxRepository.readPillBoxes().clear();
        pillBoxRepository.readMedicines().clear();

        sensorRepository.readWeightSensor().clear();

        userRepository.readCareGivers().clear();
        userRepository.readCustomers().clear();
        userRepository.readDashBoards().clear();
        userRepository.readMedSchedule().clear();


         // to implement later

        // Create Medicines



        // Create PillBoxes
        PillBox pillBox1 = new PillBox(1, 30, true);
        PillBox pillBox2 = new PillBox(2, 30, false);

        // Create WeightSensors

        // WeightSensor weightSensor1 = new WeightSensor()  to implement later

        // Create Customers
        Customer customer1 = new Customer(1, "Bob", 67, "bob@john.com", true);
        // Create Caregivers
        CareGiver careGiver1 = new CareGiver(1, "Stacy", "MyCare","10 Strawberry Lane");


        //associate the objects with their relationships
        customer1.addCaregiver(careGiver1);
        careGiver1.addCustomer(customer1);

    }






    @Override
    public void run(String... args) throws Exception {

    }
}
