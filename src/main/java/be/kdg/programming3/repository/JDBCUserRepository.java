package be.kdg.programming3.repository;

import be.kdg.programming3.domain.user.CareGiver;
import be.kdg.programming3.domain.user.Customer;
import be.kdg.programming3.domain.user.Dashboard;
import be.kdg.programming3.domain.user.MedicationSchedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@Profile("jdbc")
public class JDBCUserRepository implements UserRepository {
    private Logger logger = LoggerFactory.getLogger(JDBCUserRepository.class);
    private static List<Customer> customers = new ArrayList<>();
    private static List<CareGiver> careGivers = new ArrayList<>();
    private static List<Dashboard> dashboard = new ArrayList<>();
    private static List<MedicationSchedule> medSchedules = new ArrayList<>();
    private static AtomicInteger nextId = new AtomicInteger(1);

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert customerInserter;
    private SimpleJdbcInsert careGiverInserter;
    private SimpleJdbcInsert dashboardInserter;
    private SimpleJdbcInsert medSchedule;


    public JDBCUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("Customer")
                .usingGeneratedKeyColumns("customer_id");
        this.careGiverInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("CareGiver")
                .usingGeneratedKeyColumns("caregiver_id");
        this.dashboardInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("Dashboard")
                .usingGeneratedKeyColumns("dashboard_id");
    }


    private static final RowMapper<Customer> CUSTOMER_ROW_MAPPER = (rs, rowNum) -> {
        Customer customer = new Customer();
        customer.setCustomer_id(rs.getInt("customer_id"));
        customer.setCustomer_name(rs.getString("customer_name"));
        customer.setAge(rs.getInt("age"));
        customer.setEmail(rs.getString("email"));
        customer.setHasCareGiver(rs.getBoolean("hasCareGiver"));
        return customer;
    };

    @Override
    public Customer createCustomer(Customer customer) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("customer_id", customer.getCustomer_id());
        parameters.put("customer_name", customer.getCustomer_name());
        parameters.put("age", customer.getAge());
        parameters.put("email", customer.getEmail());
        parameters.put("hasCareGiver", customer.getCareGivers());
        customer.setCustomer_id(customerInserter.executeAndReturnKey(parameters).intValue());
        logger.info("Creating customer {}", customer);

        return customer;
    }

    @Override
    public List<Customer> findAllCustomers() {
        return jdbcTemplate.query("SELECT * FROM Customer", CUSTOMER_ROW_MAPPER);
    }

    @Override
    public findCustomerById(int customer_id) {
        return jdbcTemplate.query("SELECT * FROM Customer where customer_id = ? ",
                JDBCUserRepository::CUSTOMER_ROW_MAP, customer_id);
    }

    @Override
    public Customer updateCustomer(Customer existingCustomer) {
        logger.info("Customer updated: {}", existingCustomer);
        int index = -1;
    }

    public static Dashboard dashboardRow(ResultSet rs, int rowid) throws SQLException {
        return new Dashboard(rs.getInt("dashboard_id"),
                rs.getInt("nrPillTaken"),
                rs.getInt("duration"),
                rs.getInt("customer_id"));
    }



    @Override
    public Dashboard createDashboard(Dashboard dashboard){
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("dashboard_id", dashboard.getDashboard_id());
        parameters.put("nrPillTaken", dashboard.getNrPillTaken());
        parameters.put("duration", dashboard.getDuration());
        parameters.put("customer_id", dashboard.getCustomer().getCustomer_id());
        dashboard.setDashboard_id(dashboardInserter.executeAndReturnKey(parameters));
        logger.info("Creating a dashboard {}", dashboard);
        return dashboard;

    }

    @Override
    public List<Dashboard> findAllDashboards() {
        logger.info("Finding dashboards from the database...");
        List<Dashboard> dashboards = jdbcTemplate.query(
                "SELECT * FROM Dashboard", JDBCUserRepository::dashboardRow);
        return dashboards
    }

    @Override
    private static final RowMapper<CareGiver> CAREGIVER_ROW_MAPPER = (rs, rowNum) -> {
        CareGiver careGiver = new CareGiver();
        careGiver.setCaregiver_id(rs.getInt("caregiver_id"));
        careGiver.setCaregiver_name(rs.getString("caregiver_name"));
        careGiver.setEmail(rs.getString("email"));
        return careGiver;
    };


    @Override
    public CareGiver createCareGiver(CareGiver careGiver) {
        jdbcTemplate.update("INSERT INTO CareGiver (caregiver_name, email) VALUES (?, ?)",
                careGiver.getCaregiver_name(),
                careGiver.getEmail(), careGiver.getCaregiver_id());
        return careGiver;
    }

    @Override
    public List<CareGiver> findAllCaregivers() {
        return jdbcTemplate.query("SELECT * FROM CareGiver", CAREGIVER_ROW_MAPPER);
    }

    @Override
    public CareGiver findCaregiverById(int caregiver_id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM CareGiver WHERE caregiver_id = ?",
                    new Object[]{caregiver_id}, CAREGIVER_ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }



    @Override
    public void updateCareGiver(CareGiver careGiver) {
        jdbcTemplate.update("UPDATE CareGiver SET caregiver_name = ?, email = ? WHERE caregiver_id = ?",
                careGiver.getName(), careGiver.getEmail(), careGiver.getCaregiver_id());
    }



    @Override
    public List<MedicationSchedule> findAllMedSchedules() {
        List<MedicationSchedule> medicationSchedules = jdbcTemplate.query("SELECT * FROM MedicationSchedule",
                UserRepository::mapMedicationScheduleRow);
        return medicationSchedules;
    }

    @Override
    public MedicationSchedule findAllById(int customerId) {
        MedicationSchedule medicationSchedule = jdbcTemplate.queryForObject("SELECT * FROM MedicationSchedule WHERE customer_id = ?",
                UserRepository::mapMedicationScheduleRow, customerId);

        return medicationSchedule;
    }


    @Override
    public MedicationSchedule createMedicationSchedule(MedicationSchedule medicationSchedule) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("PILLNAME", medicationSchedule.getPillName());
        parameters.put("TIMETAKEPILL", medicationSchedule.getTimeTakePill());
        parameters.put("REPEAT_IN", medicationSchedule.getRepeatIn());
        medicationSchedule.setCustomerId(medicationScheduleInserter.executeAndReturnKey(parameters).intValue());
        return medicationSchedule;
    }


    @Override
    public void updateMedicationSchedule(MedicationSchedule medicationSchedule) {
        jdbcTemplate.update("UPDATE MEDSCHEDULE SET PILLNAME=?, TIMETAKEPILL=?, REPEAT_IN=? WHERE CUSTOMER_ID=?",
                medicationSchedule.getPillName(),medicationSchedule.getTimeTakePill(), medicationSchedule.getRepeatIn(), medicationSchedule.getCustomerId());
    }

    @Override
    public void deleteMedicationSchedule(int customerId) {
        jdbcTemplate.update("DELETE FROM MEDSCHEDULE WHERE CUSTOMER_ID=?", customerId);
    }

    @Override
    public MedicationSchedule mapResultSetToMedicationSchedule(ResultSet resultSet) throws SQLException {
        int customerId = resultSet.getInt("CUSTOMER_ID");
        String pillName = resultSet.getString("PILLNAME");
        LocalDateTime timeTakePill = resultSet.getTimestamp("TIMETAKEPILL").toLocalDateTime();
        int interval = resultSet.getInt("REPEAT_IN");
        return new MedicationSchedule(customerId, pillName, timeTakePill, interval);
    }

}
