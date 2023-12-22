package be.kdg.programming3.pillgate.repo.medSchedRepo;

import be.kdg.programming3.pillgate.domain.user.MedicationSchedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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

    public class DailyCount {
        private Date day;
        private Integer count;


        public Date getDay() {
            return day;
        }

        public void setDay(Date day) {
            this.day = day;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }
    }


    public List<DailyCount> getPillsTakenPerDay(int nDays) {
        String interval = nDays + " days";
        String sql = "SELECT DATE(timeTakePill) as day, SUM(nrOfPillsTaken) as count " +
                "FROM MedicationSchedule " +
                "WHERE timeTakePill >= CURRENT_DATE - INTERVAL '" + interval + "' " +
                "GROUP BY DATE(timeTakePill) " +
                "ORDER BY DATE(timeTakePill)";

        return jdbcTemplate.query(sql, new RowMapper<DailyCount>() {
            @Override
            public DailyCount mapRow(ResultSet rs, int rowNum) throws SQLException {
                DailyCount dailyCount = new DailyCount();
                dailyCount.setDay(rs.getDate("day"));
                dailyCount.setCount(rs.getInt("count"));
                return dailyCount;
            }
        });
    }


    @Override
    public Map<Integer, Map<String, Integer>> getTimeOfDayDataWithPillName() {
        // Initialize a map to store counts per hour and per pill name
        Map<Integer, Map<String, Integer>> countsPerHourPerPill = new HashMap<>();

        // Define the SQL query
        String sql = "SELECT EXTRACT(HOUR FROM timeTakePill) AS hourOfDay, pillName, sum(nrofpillstaken) as count " +
                "FROM MedicationSchedule " +
                "WHERE timeTakePill IS NOT NULL " +
                "GROUP BY hourOfDay, pillName " +
                "ORDER BY hourOfDay, pillName";

        // Execute the query and update the countsPerHourPerPill map
        jdbcTemplate.query(sql, rs -> {
            int hour = rs.getInt("hourOfDay");
            String pillName = rs.getString("pillName");
            int count = rs.getInt("count");

            // Initialize the inner map if it does not exist for the hour
            countsPerHourPerPill.computeIfAbsent(hour, k -> new HashMap<>());

            // Update the count for the pill name at the hour
            countsPerHourPerPill.get(hour).put(pillName, count);
        });

        return countsPerHourPerPill;
    }



}
