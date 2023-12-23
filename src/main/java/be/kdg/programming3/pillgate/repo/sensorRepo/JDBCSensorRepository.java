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

    public WeightSensorData mapRow(ResultSet rs, int rowId) throws SQLException {
        return new WeightSensorData(
                rs.getInt("sensor_ID"),
                rs.getInt("customer_id"),
                rs.getTimestamp("calibrationDate").toLocalDateTime(),
                rs.getDouble("weight"));
    }


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



    @Override
    public List<WeightSensorData> findAllWSensors() {
        logger.info("Reading weightSensors from the database...");
        return jdbcTemplate.query("SELECT * FROM WeightSensor ORDER BY calibrationDate DESC LIMIT 2", this::mapRow);
    }

    @Override
    public WeightSensorData findSensorByID(int sensor_ID) {
        WeightSensorData weightSensorData = jdbcTemplate.queryForObject("SELECT * FROM WeightSensor WHERE sensor_ID = ?", this::mapRow, sensor_ID);
        logger.info("Finding weight sensor by id {} ", weightSensorData.getSensor_ID());
        return weightSensorData;
    }


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