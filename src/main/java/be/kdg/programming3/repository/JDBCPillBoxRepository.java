//package be.kdg.programming3.repository;
//
//import be.kdg.programming3.domain.user.MedicationSchedule;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Profile;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.stereotype.Repository;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.time.LocalDate;
//import java.util.List;
//
//@Repository
//@Profile("jdbc")
//public class JDBCPillBoxRepository implements MedicationScheduleRepository {
//
//    private static final Logger logger = LoggerFactory.getLogger(JDBCPillBoxRepository.class);
//    private final JdbcTemplate jdbcTemplate;
//
//    public JDBCPillBoxRepository(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    private static final RowMapper<MedicationSchedule> medicationScheduleRowMapper = new RowMapper<MedicationSchedule>() {
//        @Override
//        public MedicationSchedule mapRow(ResultSet rs, int rowNum) throws SQLException {
//            int medScheduleId = rs.getInt("medSchedule_id");
//            int customerId = rs.getInt("customer_id");
//            LocalDate startDate = rs.getDate("startDate").toLocalDate();
//            LocalDate endDate = rs.getDate("endDate").toLocalDate();
//            String pillName = rs.getString("pillName");
//            int quantity = rs.getInt("quantity");
//            LocalDate timeTakePill = rs.getDate("timeTakePill").toLocalDate();
//
//            return new MedicationSchedule(customerId, startDate, endDate, pillName, quantity, timeTakePill);
//        }
//    };
//
//    @Override
//    public MedicationSchedule createMedicationSchedule(MedicationSchedule schedule) {
//        final String sql = "INSERT INTO MedicationSchedule (customer_id, startDate, endDate, pillName, quantity, timeTakePill) VALUES (?, ?, ?, ?, ?, ?)";
//        jdbcTemplate.update(sql, schedule.getCustomerId(), schedule.getStartDate(), schedule.getEndDate(), schedule.getPillName(), schedule.getQuantity(), schedule.getTimeTakePill());
//        return schedule;
//    }
//
//    @Override
//    public MedicationSchedule findMedicationScheduleById(int medScheduleId) {
//        final String sql = "SELECT * FROM MedicationSchedule WHERE medSchedule_id = ?";
//        return jdbcTemplate.queryForObject(sql, new Object[]{medScheduleId}, medicationScheduleRowMapper);
//    }
//
//    @Override
//    public List<MedicationSchedule> findAllMedicationSchedules() {
//        final String sql = "SELECT * FROM MedicationSchedule";
//        return jdbcTemplate.query(sql, medicationScheduleRowMapper);
//    }
//
//    @Override
//    public MedicationSchedule updateMedicationSchedule(MedicationSchedule schedule) {
//        final String sql = "UPDATE MedicationSchedule SET customer_id = ?, startDate = ?, endDate = ?, pillName = ?, quantity = ?, timeTakePill = ? WHERE medSchedule_id = ?";
//        int affectedRows = jdbcTemplate.update(sql, schedule.getCustomerId(), schedule.getStartDate(), schedule.getEndDate(), schedule.getPillName(), schedule.getQuantity(), schedule.getTimeTakePill(), schedule.getMedSchedule_id());
//        return affectedRows > 0 ? schedule : null;
//    }
//
//    @Override
//    public MedicationSchedule deleteMedicationSchedule(int medScheduleId) {
//        final String sql = "DELETE FROM MedicationSchedule WHERE medSchedule_id = ?";
//        int affectedRows = jdbcTemplate.update(sql, medScheduleId);
//        return affectedRows > 0 ? new MedicationSchedule() : null;
//    }
//}
