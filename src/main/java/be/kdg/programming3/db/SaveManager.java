/*
package be.kdg.programming3.db;

import be.kdg.programming3.domain.sensor.WeightSensor;
import be.kdg.programming3.domain.user.CareGiver;
import be.kdg.programming3.domain.user.Dashboard;
import be.kdg.programming3.domain.user.MedicationSchedule;

import java.sql.*;

public class SaveManager {
    public static int saveWeightSensor(Connection connection, WeightSensor weightSensor) {
        String sql = "INSERT INTO WeightSensor (WEIGHT_CAPACITY_GRAMS, calibrationDate, weight) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, weightSensor.getWeightCapacityGrams());
            statement.setDate(2, Date.valueOf(weightSensor.getCalibrationDate()));
            statement.setDouble(3, weightSensor.getWeight());
            return executeInsert(statement);
        } catch (SQLException e) {
            handleSqlException(e);
            return -1;
        }
    }

    public static int saveCareGiver(Connection connection, CareGiver careGiver) {
        String sql = "INSERT INTO CareGiver (caregiver_name) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, careGiver.getCaregiverName());
            return executeInsert(statement);
        } catch (SQLException e) {
            handleSqlException(e);
            return -1;
        }
    }

    public static int saveDashboard(Connection connection, Dashboard dashboard) {
        String sql = "INSERT INTO Dashboard (nrPillTaken, duration) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, dashboard.getNrPillTaken());
            statement.setInt(2, dashboard.getDuration());
            return executeInsert(statement);
        } catch (SQLException e) {
            handleSqlException(e);
            return -1;
        }

    }
    public static int saveMedicationSchedule(Connection connection, MedicationSchedule medSchedule) {
        String sql = "INSERT INTO MedicationSchedule (customer_id, startDate, endDate, pillName, quantity, timeTakePill) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, medSchedule.getCustomerId());
            statement.setDate(2, Date.valueOf(medSchedule.getStartDate()));
            statement.setDate(3, Date.valueOf(medSchedule.getEndDate()));
            statement.setString(4, medSchedule.getPillName());
            statement.setInt(5, medSchedule.getQuantity());
            statement.setDate(6, Date.valueOf(medSchedule.getTimeTakePill()));
            return executeInsert(statement);
        } catch (SQLException e) {
            handleSqlException(e);
            return -1;
        }
    }




}
*/
