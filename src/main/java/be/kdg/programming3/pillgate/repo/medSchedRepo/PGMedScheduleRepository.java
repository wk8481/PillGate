package be.kdg.programming3.pillgate.repo.medSchedRepo;

import be.kdg.programming3.pillgate.domain.user.MedicationSchedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.kdg.programming3.pillgate.domain.user.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Profile("postgres")
@Primary
public class PGMedScheduleRepository implements MedScheduleRepository {

    private static Logger logger = LoggerFactory.getLogger(PGMedScheduleRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public PGMedScheduleRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        logger.info("Setting up the PostgreSQL med schedule repository...");
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<MedicationSchedule> findAllMedSchedules() {
        logger.info("Finding medication schedules...");
        String selectQuery = "SELECT * FROM MedicationSchedule";
        return jdbcTemplate.query(selectQuery, new MedScheduleRowMapper());
    }

    @Override
    public MedicationSchedule findMedScheduleById(int medSchedule_id) {
        logger.info("Finding medication schedules by id: {}", medSchedule_id);
        String selectQuery = "SELECT * FROM MedicationSchedule WHERE medSchedule_id = :medSchedule_id";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("medSchedule_id", medSchedule_id);

        return jdbcTemplate.queryForObject(selectQuery, parameters, new MedScheduleRowMapper());
    }

    @Override
    public MedicationSchedule createMedSchedule(MedicationSchedule medSchedule) {
        logger.info("Creating med schedule: {}", medSchedule);
        String insertQuery = "INSERT INTO MedicationSchedule(customer_id, pillName, repeatIn, " +
                "timeTakePill, nrOfPillsPlaced, weightOfSinglePill, nrOfPillsTaken, isStopped, message) " +
                "VALUES (:customer_id, :pillName, :repeatIn, :timeTakePill, " +
                ":nrOfPillsPlaced, :weightOfSinglePill, :nrOfPillsTaken, :isStopped, :message)";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("customer_id", medSchedule.getCustomer().getCustomer_id());
        parameters.put("pillName", medSchedule.getPillName());
        parameters.put("repeatIn", medSchedule.getRepeatIn());
        parameters.put("timeTakePill", medSchedule.getTimeTakePill());
        parameters.put("nrOfPillsPlaced", medSchedule.getNrOfPillsPlaced());
        parameters.put("weightOfSinglePill", medSchedule.getWeightOfSinglePill());
        parameters.put("nrOfPillsTaken", medSchedule.getNrOfPillsTaken());
        parameters.put("isStopped", medSchedule.isStopped());
        parameters.put("message", medSchedule.getMessage());

        int medScheduleId = jdbcTemplate.update(insertQuery, parameters);
        medSchedule.setMedSchedule_id(medScheduleId);
        logger.info("Medication schedule created: {}", medSchedule);

        return medSchedule;
    }

    @Override
    public MedicationSchedule updateMedSchedule(MedicationSchedule medicationSchedule) {
        logger.info("Updating med schedule: {}", medicationSchedule);
        String updateQuery = "UPDATE MedicationSchedule SET " +
                "pillName=:pillName, timeTakePill=:timeTakePill, nrOfPillsPlaced=:nrOfPillsPlaced, nrOfPillsTaken=:nrOfPillsTaken WHERE medSchedule_id=:medSchedule_id";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("pillName", medicationSchedule.getPillName());
        parameters.put("timeTakePill", medicationSchedule.getTimeTakePill());
        parameters.put("nrOfPillsPlaced", medicationSchedule.getNrOfPillsPlaced());
        parameters.put("nrOfPillsTaken", medicationSchedule.getNrOfPillsTaken());
        parameters.put("medSchedule_id", medicationSchedule.getMedSchedule_id());

        int updatedRows = jdbcTemplate.update(updateQuery, parameters);

        if (updatedRows > 0) {
            logger.info("Medication schedule updated: {}", medicationSchedule);
            return medicationSchedule;
        } else {
            logger.warn("Failed to update medication schedule with ID: {}", medicationSchedule.getMedSchedule_id());
            return null;
        }
    }

    @Override
    public MedicationSchedule deleteMedSchedule(int medicationSchedule_id) {
        logger.info("Deleting medication schedule by id: {}", medicationSchedule_id);
        String deleteQuery = "DELETE FROM MedicationSchedule WHERE medSchedule_id = :medSchedule_id";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("medSchedule_id", medicationSchedule_id);

        MedicationSchedule deletedMedSchedule = findMedScheduleById(medicationSchedule_id);
        jdbcTemplate.update(deleteQuery, parameters);

        return deletedMedSchedule;
    }

    // Inner class to map rows to MedicationSchedule objects
    private static class MedScheduleRowMapper implements org.springframework.jdbc.core.RowMapper<MedicationSchedule> {
        @Override
        public MedicationSchedule mapRow(ResultSet rs, int rowNum) throws java.sql.SQLException {
            MedicationSchedule medicationSchedule = new MedicationSchedule();
            medicationSchedule.setMedSchedule_id(rs.getInt("medSchedule_id"));
            medicationSchedule.setCustomer_id(rs.getInt("customer_id"));
            medicationSchedule.getCustomer().setCustomer_id(rs.getInt("customer_id"));
            medicationSchedule.setPillName(rs.getString("pillName"));
            medicationSchedule.setRepeatIn(rs.getInt("repeatIn"));
            medicationSchedule.setTimeTakePill(rs.getTimestamp("timeTakePill").toLocalDateTime());
            medicationSchedule.setNrOfPillsPlaced(rs.getInt("nrOfPillsPlaced"));
            medicationSchedule.setWeightOfSinglePill(rs.getDouble("weightOfSinglePill"));
            medicationSchedule.setNrOfPillsTaken(rs.getInt("nrOfPillsTaken"));
            // Set other properties as needed
            return medicationSchedule;
        }
    }

}
