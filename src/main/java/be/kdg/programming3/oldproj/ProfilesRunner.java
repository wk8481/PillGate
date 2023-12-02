package be.kdg.programming3.oldproj;

import be.kdg.programming3.oldproj.repository.JDBCSensorRepository;
import be.kdg.programming3.oldproj.repository.JDBCUserRepository;
import org.springframework.boot.CommandLineRunner;

public class ProfilesRunner implements CommandLineRunner {

private JDBCUserRepository jdbcUserRepository;
private JDBCSensorRepository jdbcSensorRepository;

    public ProfilesRunner(JDBCUserRepository jdbcUserRepository, JDBCSensorRepository jdbcSensorRepository) {
        this.jdbcUserRepository = jdbcUserRepository;
        this.jdbcSensorRepository = jdbcSensorRepository;
    }

    public void run(String... args) throws Exception{
        System.out.println("\n>> ProfilesRunner");
        System.out.println(jdbcUserRepository.findAllDashboards());
        System.out.println(jdbcUserRepository.findAllMedSchedules());
        System.out.println(jdbcSensorRepository.findAllWSensors());
        System.out.println(jdbcUserRepository.findAllCareGivers());
        System.out.println(jdbcUserRepository.findAllCustomers());

    }

}
