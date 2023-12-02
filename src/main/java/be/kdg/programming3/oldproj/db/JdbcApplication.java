package be.kdg.programming3.oldproj.db;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@Component
public class JdbcApplication implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:PillGatedb;DB_CLOSE_ON_EXIT=FALSE")) {
            Statement statement = connection.createStatement();

            statement.executeUpdate("INSERT INTO WeightSensor (WEIGHT_CAPACITY_GRAMS, calibrationDate, weight) VALUES (1000, '2022-01-01', 750)");
            statement.executeUpdate("INSERT INTO CareGiver (caregiver_name, email) VALUES ('chao', 'chao@example.com')");
            statement.executeUpdate("INSERT INTO Customer (customer_name, BIRTHDATE, email, hasCareGiver, password) VALUES ('Alice Smith', '1990-01-01', 'alice@example.com', true, 'passwordHash')");
            statement.executeUpdate("INSERT INTO Dashboard (nrPillTaken, duration, customer_id) VALUES (5, 2, 1)");
            statement.executeUpdate("INSERT INTO MedicationSchedule (customer_id, startDate, endDate, pillName, quantity, timeTakePill) VALUES (1, '2022-01-01', '2022-12-31', 'Ibuprofen', 2, '2022-01-01 08:00:00')");
            statement.executeUpdate("INSERT INTO CustomerCareGiver (customer_id, caregiver_id) VALUES (1, 1)");

            // Sample query for each table
            queryAndPrint(connection, "SELECT * FROM WeightSensor", "WeightSensor");
            queryAndPrint(connection, "SELECT * FROM CareGiver", "CareGiver");
            queryAndPrint(connection, "SELECT * FROM Customer", "Customer");
            queryAndPrint(connection, "SELECT * FROM Dashboard", "Dashboard");
            queryAndPrint(connection, "SELECT * FROM MedicationSchedule", "MedicationSchedule");
        }
    }

    private void queryAndPrint(Connection connection, String query, String tableName) throws Exception {
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
            System.out.println("Data from " + tableName + ":");
            while (resultSet.next()) {
                switch (tableName) {
                    case "WeightSensor" ->
                            System.out.println(resultSet.getInt("sensor_ID") + " " + resultSet.getInt("WEIGHT_CAPACITY_GRAMS") + " " + resultSet.getDate("calibrationDate") + " " + resultSet.getDouble("weight") + " " + resultSet.getInt("customer_id"));
                    case "CareGiver" ->
                            System.out.println(resultSet.getInt("caregiver_id") + " " + resultSet.getString("caregiver_name") + " " + resultSet.getString("email"));
                    case "Customer" ->
                            System.out.println(resultSet.getInt("customer_id") + " " + resultSet.getString("customer_name") + " " + resultSet.getInt("birthdate") + " " + resultSet.getString("email") + " " + resultSet.getBoolean("hasCareGiver") + " " + resultSet.getString("password"));
                    case "Dashboard" ->
                            System.out.println(resultSet.getInt("dashboard_id") + " " + resultSet.getInt("nrPillTaken") + " " + resultSet.getInt("duration") + " " + resultSet.getInt("customer_id"));
                    case "MedicationSchedule" ->
                            System.out.println(resultSet.getInt("medSchedule_id") + " " + resultSet.getInt("customer_id") + " " + resultSet.getDate("startDate") + " " + resultSet.getDate("endDate") + " " + resultSet.getString("pillName") + " " + resultSet.getInt("quantity") + " " + resultSet.getDate("timeTakePill") );
                    case "CustomerCareGiver" ->
                            System.out.println(resultSet.getInt("customer_id") + " " + resultSet.getInt("caregiver_id"));
                    default -> System.out.println("Unknown table");
                }
            }
        }
    }
}
