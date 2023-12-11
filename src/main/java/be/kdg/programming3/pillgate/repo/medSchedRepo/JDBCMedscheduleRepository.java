package be.kdg.programming3.pillgate.repo.medSchedRepo;

import be.kdg.programming3.pillgate.domain.user.MedicationSchedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("jdbctemplate")
@Primary
public class JDBCMedscheduleRepository implements MedScheduleRepository{
    private static List<MedicationSchedule> medSchedules = new ArrayList<>();

    private final Logger logger = LoggerFactory.getLogger(JDBCMedscheduleRepository.class);
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert medScheduleInserter;

    public JDBCMedscheduleRepository(JdbcTemplate jdbcTemplate) {
        logger.info("Setting up the medschedulerepository...");
        this.jdbcTemplate=jdbcTemplate;
        this.medScheduleInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("MedicationSchedule")
                .usingGeneratedKeyColumns("medSchedule_id");
    }



    private static final RowMapper<MedicationSchedule> MEDICATION_SCHEDULE_ROW_MAPPER = (rs, rowNum) -> {
        MedicationSchedule medicationSchedule = new MedicationSchedule();
        medicationSchedule.setMedSchedule_id(rs.getInt("medSchedule_id"));
        medicationSchedule.getCustomer().setCustomer_id(rs.getInt("customer_id"));
        medicationSchedule.setStartDate(rs.getDate("startDate").toLocalDate());
        medicationSchedule.setEndDate(rs.getDate("endDate").toLocalDate());
        medicationSchedule.setPillName(rs.getString("pillName"));
        medicationSchedule.setQuantity(rs.getInt("quantity"));
        medicationSchedule.setTimeTakePill(rs.getTimestamp("timeTakePill").toLocalDateTime());
        medicationSchedule.setRepeatIn(rs.getInt("repeatIn"));
        medicationSchedule.setStopped(rs.getBoolean("isStopped"));
        medicationSchedule.setMessage(rs.getString("message"));
        // Set other properties as needed
        return medicationSchedule;
    };

    @Override
    public List<MedicationSchedule> findAllMedSchedules() {
        logger.info("Finding medication schedules...");
        return jdbcTemplate.query("SELECT * FROM MedicationSchedule", MEDICATION_SCHEDULE_ROW_MAPPER);
    }

    @Override
    public MedicationSchedule findMedScheduleById(int medSchedule_id) {
        logger.info("Finding medication schedules by id");

        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM MedicationSchedule WHERE customer_id = ?",
                    new Object[]{medSchedule_id},
                    MEDICATION_SCHEDULE_ROW_MAPPER
            );
        } catch (EmptyResultDataAccessException e) {
            return null; // Or handle the exception as needed
        }
    }

    @Override
    public MedicationSchedule createMedSchedule(MedicationSchedule medSchedule) {
        logger.info("Creating medschedule");
        jdbcTemplate.update("INSERT INTO MedicationSchedule(medSchedule_id,customer_id,startDate,endDate,pillName,quantity,timeTakePill,isStopped,message) " +
                        "VALUES (?,?,?,?,?,?,?,?,?)",
                medSchedule.getMedSchedule_id(),
                medSchedule.getCustomer().getCustomer_id(),
                medSchedule.getStartDate(),
                medSchedule.getEndDate(),
                medSchedule.getPillName(),
                medSchedule.getQuantity(),
                medSchedule.getTimeTakePill(),
                medSchedule.isStopped(),
                medSchedule.getMessage());
        return medSchedule;
    }

    @Override
    public MedicationSchedule updateMedSchedule(MedicationSchedule medicationSchedule) {
        logger.info("Updating medschedule...");
        jdbcTemplate.update(
                "UPDATE MedicationSchedule SET startDate=?, endDate=?, pillName=?, quantity=?, timeTakePill=? WHERE medSchedule_id=?",
                medicationSchedule.getStartDate(),
                medicationSchedule.getEndDate(),
                medicationSchedule.getPillName(),
                medicationSchedule.getQuantity(),
                medicationSchedule.getTimeTakePill(),
                medicationSchedule.getMedSchedule_id()
        );
        return medicationSchedule;
    }

    @Override
    public MedicationSchedule deleteMedSchedule(int medicationSchedule_id) {
        MedicationSchedule deletedMedSchedule = findMedScheduleById(medicationSchedule_id);

        jdbcTemplate.update("DELETE FROM MedicationSchedule WHERE medSchedule_id=?", medicationSchedule_id);

        return deletedMedSchedule;
    }

    public boolean existsById(int customerId) {
        String sql = "SELECT COUNT(*) FROM Customer WHERE customer_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{customerId}, Integer.class);
        return count != null && count > 0;
    }
}

