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

import java.time.LocalDate;
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

        // Create Medicines
        Medicine medicine1 = new Medicine("Aspirin", "White", "Circular", "Painkiller", 20, 1);

        // Create WeightSensors
        WeightSensor weightSensor1 = new WeightSensor(1,"Load Sensor", "Fundoino", LocalDate.of(2023, 11, 14 ));

        // WeightSensor weightSensor1 = new WeightSensor()  to implement later

        // Create Customers
        Customer customer1 = new Customer(1, "Bob", 67, "bob@john.com", true);
        Customer customer2 = new Customer(2, "John", 50, "john@bob.com", false);
        // Create Caregivers
        CareGiver careGiver1 = new CareGiver(1, "Stacy", "MyCare","10 Strawberry Lane");


        // Create Medication Schedule
        MedicationSchedule medicationSchedule1 = new MedicationSchedule(1, LocalDate.of(2021, 2,13), LocalDate.of(2023, 2,20), "Aspirin", 5, LocalDate.of(2023, 2, 21));


        // Create Dashboard
        Dashboard dashboard1 = new Dashboard(1, 3, 4 );

        //associate the objects with their relationships
        customer1.addCaregiver(careGiver1);
        careGiver1.addCustomer(customer1);

customer1.addMedicationSchedule(medicationSchedule1);
medicationSchedule1.addCustomer(customer1);
medicine1.addPillbox(pillBox1);
pillBox1.addMedicine(medicine1);
pillBox1.addWeightSensor(weightSensor1);
weightSensor1.addPillbox(pillBox1);
medicine1.addCustomer(customer1);
customer1.addMedicine(medicine1);
customer1.addCaregiver(careGiver1);
careGiver1.addCustomer(customer1);
medicationSchedule1.addDashboard(dashboard1);
dashboard1.addMedicationSchedule(medicationSchedule1);

            // Add entities to lists
            medicines.add(medicine1);

            pillBoxes.add(pillBox1);
            pillBoxes.add(pillBox2);

            weightSensors.add(weightSensor1);

            customers.add(customer1);
            customers.add(customer2);

            dashboards.add(dashboard1);

            medicationSchedules.add(medicationSchedule1);

            careGivers.add(careGiver1);

            // Add lists to repositories
            pillBoxRepository.readMedicines().addAll(medicines);
            pillBoxRepository.readPillBoxes().addAll(pillBoxes);

            sensorRepository.readWeightSensor().addAll(weightSensors);

            userRepository.readCareGivers().addAll(careGivers);
            userRepository.readCustomers().addAll(customers);
            userRepository.readDashBoards().addAll(dashboards);
            userRepository.readMedSchedule().addAll(medicationSchedules);






    }






    @Override
    public void run(String... args) throws Exception {
        seed();

    }
}
