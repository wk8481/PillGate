package be.kdg.programming3.pillgate.repo.sensorRepo;


import jakarta.validation.constraints.NotNull;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import be.kdg.programming3.pillgate.domain.sensor.WeightSensor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Profile("postgres")
@Primary
public class PGSensorRepository implements SensorRepository {

    private final Logger logger = LoggerFactory.getLogger(PGSensorRepository.class);

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert sensorInserter;

    @Autowired
    public PGSensorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.sensorInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("WeightSensor")
                .usingGeneratedKeyColumns("sensor_ID");
        logger.info("Setting up the sensor repository...");
    }

    private static final RowMapper<WeightSensor> SENSOR_ROW_MAPPER = (rs, rowNum) -> new WeightSensor(
            rs.getInt("sensor_ID"),
            rs.getInt("customer_id"),
            rs.getInt("WEIGHT_CAPACITY_GRAMS"),
            rs.getDate("calibrationDate").toLocalDate(),
            rs.getDouble("weight")
    );

/*    @Override
    public WeightSensor createSensor(WeightSensor weightSensor) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("sensor_ID", weightSensor.getSensor_ID());
        return getWeightSensor(weightSensor, parameters, sensorInserter, logger);
    }*/
    @Override
    public WeightSensor createSensor(WeightSensor weightSensor) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("sensorID", weightSensor.getSensor_ID());
        parameters.put("customer_id", weightSensor.getCustomer().getCustomer_id());
        parameters.put("WEIGHT_CAPACITY_GRAMS", weightSensor.getWEIGHT_CAPACITY_GRAMS());
        parameters.put("calibrationDate", weightSensor.getCalibrationDate());
        parameters.put("weight", weightSensor.getWeight());

        // Execute the insert and get the auto-generated key
        Number newId = sensorInserter.executeAndReturnKey(parameters);
        weightSensor.setSensor_ID(newId.intValue());

        logger.info("Creating weight sensor {}", weightSensor);
        return weightSensor;
    }

/*    @NotNull
    static WeightSensor getWeightSensor(WeightSensor weightSensor, Map<String, Object> parameters, SimpleJdbcInsert sensorInserter, Logger logger) {
        parameters.put("customer_id", weightSensor.getCustomer().getCustomer_id());
        parameters.put("WEIGHT_CAPACITY_GRAMS", weightSensor.getWEIGHT_CAPACITY_GRAMS());
        parameters.put("calibrationDate", weightSensor.getCalibrationDate());
        parameters.put("weight", weightSensor.getWeight());

        Number newId = sensorInserter.executeAndReturnKey(parameters);
        weightSensor.setSensor_ID(newId.intValue());

        logger.info("Creating weight sensor {}", weightSensor);
        return weightSensor;
    }*/

    @Override
    public List<WeightSensor> findAllWSensors() {
        logger.info("Reading weightSensors from the database...");
        return jdbcTemplate.query("SELECT * FROM WeightSensor", SENSOR_ROW_MAPPER);
    }

    @Override
    public WeightSensor findSensorByID(int sensor_ID) {
        logger.info("Finding weight sensor by id: {}", sensor_ID);
        return jdbcTemplate.queryForObject("SELECT * FROM WeightSensor WHERE sensor_ID = ?",
                new Object[]{sensor_ID}, SENSOR_ROW_MAPPER);
    }

    @Override
    public WeightSensor updateSensor(WeightSensor existingWSensor) {
        logger.info("Updating weight sensor: {}", existingWSensor);

        String sql = "UPDATE WeightSensor SET WEIGHT_CAPACITY_GRAMS = ?, calibrationDate = ?, weight = ? WHERE sensor_ID = ?";

        int updatedRows = jdbcTemplate.update(sql,
                existingWSensor.getWEIGHT_CAPACITY_GRAMS(),
                existingWSensor.getCalibrationDate(),
                existingWSensor.getWeight(),
                existingWSensor.getSensor_ID());

        if (updatedRows > 0) {
            logger.info("Weight sensor updated successfully: {}", existingWSensor);
            return existingWSensor;
        } else {
            logger.warn("No weight sensor found with ID: {}", existingWSensor.getSensor_ID());
            return null;
        }
    }
}

