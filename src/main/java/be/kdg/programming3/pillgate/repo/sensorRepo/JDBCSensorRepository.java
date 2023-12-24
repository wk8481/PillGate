package be.kdg.programming3.pillgate.repo.sensorRepo;

import be.kdg.programming3.pillgate.domain.sensor.WeightSensorData;
import be.kdg.programming3.pillgate.repo.customerRepo.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * The {@code JDBCSensorRepository} class is an implementation of the {@link SensorRepository} interface
 * @author Team PillGate
 * @see SensorRepository
 * @see WeightSensorData
 */

//TODO: add javadoc alperen
@Profile("postgres")
@Repository
//@Primary
public class JDBCSensorRepository implements SensorRepository {
    private final Logger logger = LoggerFactory.getLogger(JDBCSensorRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert sensorInserter;

    public JDBCSensorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.sensorInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("WeightSensorData")
                .usingGeneratedKeyColumns("calibrationDate");
        logger.info("Setting up the sensor repository...");
    }

    /**
     * Maps a row from the database result set to a {@link WeightSensorData} object.
     *
     * @param rs The database result set.
     * @param rowId The ID of the row to map.
     * @return A {@link WeightSensorData} object mapped from the database result set.
     * @throws SQLException If an SQL exception occurs during result set processing.
     */
    public WeightSensorData mapRow(ResultSet rs, int rowId) throws SQLException {
        return new WeightSensorData(
                rs.getInt("sensor_ID"),
                rs.getInt("customer_id"),
                rs.getTimestamp("calibrationDate").toLocalDateTime(),
                rs.getDouble("weight"));
    }


    /**
     * Creates a new {@link WeightSensorData} entry in the database with the provided data.
     *
     * @param weightSensorData The weight sensor data to be created in the database.
     * @return The created weight sensor data.
     */

    @Override
    public WeightSensorData createSensor(WeightSensorData weightSensorData) {
        jdbcTemplate.update("INSERT INTO WeightSensor (customer_id, calibrationDate, weight) " +
                        "VALUES (?, ?, ?)",
                weightSensorData.getCustomer().getCustomer_id(),
                weightSensorData.getCalibrationDate(),
                weightSensorData.getWeight());
        logger.info("Creating weight sensor {}", weightSensorData);
        return weightSensorData;
    }


    /**
     * Retrieves a list of the two most recent {@link WeightSensorData} entries from the database,
     * ordered by calibration date in descending order.
     *
     * @return A list of the two most recent weight sensor data entries.
     */

    @Override
    public List<WeightSensorData> findAllWSensors() {
        logger.info("Reading weightSensors from the database...");
        return jdbcTemplate.query("SELECT * FROM WeightSensor ORDER BY calibrationDate DESC LIMIT 2", this::mapRow);
    }

    /**
     * Retrieves a {@link WeightSensorData} entry from the database based on its sensor ID.
     *
     * @param sensor_ID The ID of the weight sensor data to retrieve.
     * @return The weight sensor data with the specified sensor ID.
     */
    @Override
    public WeightSensorData findSensorByID(int sensor_ID) {
        WeightSensorData weightSensorData = jdbcTemplate.queryForObject("SELECT * FROM WeightSensor WHERE sensor_ID = ?", this::mapRow, sensor_ID);
        logger.info("Finding weight sensor by id {} ", weightSensorData.getSensor_ID());
        return weightSensorData;
    }

    /**
     * Updates an existing {@link WeightSensorData} entry in the database with the provided data.
     *
     * @param existingWSensor The existing weight sensor data to be updated.
     * @return The updated weight sensor data if the update is successful, or {@code null} otherwise.
     */
   @Override
    public WeightSensorData updateSensor(WeightSensorData existingWSensor) {
        logger.info("Updating weight sensor: {}", existingWSensor);

        String sql = "UPDATE WeightSensor SET calibrationDate = ?, weight = ? WHERE sensor_ID = ?";

        int updatedRows = jdbcTemplate.update(sql,
                existingWSensor.getCalibrationDate(),
                existingWSensor.getWeight(),
                existingWSensor.getSensor_ID());

        if (updatedRows > 0) {
            logger.info("Weight sensor updated successfully: {}", existingWSensor);
            return existingWSensor;
        } else {
            //logger.warn("No weight sensor found with ID: {}", existingWSensor.getSensor_ID());
            logger.warn("No weight sensor found with ID: {}", existingWSensor.getCustomer().getCustomer_id());
            return null;
        }

    }

}