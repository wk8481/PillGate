package be.kdg.programming3.pillgate.repo.sensorRepo;

import be.kdg.programming3.pillgate.domain.sensor.WeightSensor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
                .withTableName("WeightSensor")
                .usingGeneratedKeyColumns("calibrationDate");
        logger.info("Setting up the sensor repository...");
    }

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
        String sqlinsert = "INSERT INTO WeightSensor (customer_id, calibrationDate, weight) VALUES ( ?, ?, ?)";

        int updatedRows = jdbcTemplate.update(sql,
                existingWSensor.getCalibrationDate(),
                existingWSensor.getWeight(),
                existingWSensor.getSensor_ID());


        jdbcTemplate.update(sqlinsert,
                existingWSensor.getCustomer().getCustomer_id(),
                existingWSensor.getCalibrationDate(),
                existingWSensor.getWeight());

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