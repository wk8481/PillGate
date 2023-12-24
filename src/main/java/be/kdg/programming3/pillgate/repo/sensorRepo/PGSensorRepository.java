/*
package be.kdg.programming3.pillgate.repo.sensorRepo;

import be.kdg.programming3.pillgate.domain.sensor.WeightSensor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

*/
/**
 * The {@code PGSensorRepository} class is an implementation of the {@link SensorRepository} interface
 * that interacts with a PostgreSQL database. It provides methods to perform CRUD operations on the WeightSensor entities.
 *
 * <p>This class is part of the PillGate application developed by Team PillGate.</p>
 *
 * @author Team PillGate
 * @see SensorRepository
 * @see WeightSensor
 *//*

@Profile("postgres")
@Repository
//@Primary
public class PGSensorRepository implements SensorRepository {
    private final Logger logger = LoggerFactory.getLogger(PGSensorRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert sensorInserter;

    */
/**
     * Constructs a new {@code PGSensorRepository} with the specified {@link JdbcTemplate}.
     *
     * @param jdbcTemplate The {@link JdbcTemplate} used to interact with the PostgreSQL database.
     *//*

    public PGSensorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.sensorInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("WeightSensor")
                .usingGeneratedKeyColumns("calibrationDate");
        logger.info("Setting up the sensor repository...");
    }

    */
/**
     * Maps a row in the database to a {@link WeightSensor} object.
     *
     * @param rs     The result set representing a row in the database.
     * @param rowId  The row ID.
     * @return A {@link WeightSensor} object mapped from the database row.
     * @throws SQLException If a SQL exception occurs.
     *//*

    public WeightSensor mapRow(ResultSet rs, int rowId) throws SQLException {
        return new WeightSensor(
                rs.getInt("sensor_ID"),
                rs.getInt("customer_id"),
                rs.getTimestamp("calibrationDate").toLocalDateTime(),
                rs.getDouble("weight"));
    }

    @Override
    public WeightSensor createSensor(WeightSensor weightSensor) {
        jdbcTemplate.update("INSERT INTO WeightSensor (customer_id, calibrationDate, weight) " +
                        "VALUES (?, ?, ?)",
                weightSensor.getCustomer().getCustomer_id(),
                weightSensor.getCalibrationDate(),
                weightSensor.getWeight());
        logger.info("Creating weight sensor {}", weightSensor);
        return weightSensor;
    }

    @Override
    public List<WeightSensor> findAllWSensors() {
        logger.info("Reading weightSensors from the database...");
        return jdbcTemplate.query("SELECT * FROM WeightSensor ORDER BY calibrationDate DESC LIMIT 2", this::mapRow);
    }

    @Override
    public WeightSensor findSensorByID(int sensor_ID) {
        WeightSensor weightSensor = jdbcTemplate.queryForObject("SELECT * FROM WeightSensor WHERE sensor_ID = ?", this::mapRow, sensor_ID);
        logger.info("Finding weight sensor by id {} ", weightSensor.getSensor_ID());
        return weightSensor;
    }

    @Override
    public WeightSensor updateSensor(WeightSensor existingWSensor) {
        logger.info("Updating weight sensor: {}", existingWSensor);

        String sql = "UPDATE WeightSensor SET calibrationDate = ?, weight = ? WHERE customer_id = ?";

        int updatedRows = jdbcTemplate.update(sql,
                existingWSensor.getCalibrationDate(),
                existingWSensor.getWeight(),
                existingWSensor.getSensor_ID());

        if (updatedRows > 0) {
            logger.info("Weight sensor updated successfully: {}", existingWSensor);
            return existingWSensor;
        } else {
            logger.warn("No weight sensor found with ID: {}", existingWSensor.getCustomer().getCustomer_id());
            return null;
        }
    }
}
*/
