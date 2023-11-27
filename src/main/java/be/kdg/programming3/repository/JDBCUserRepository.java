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
import org.springframework.stereotype.Repository;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
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
    private SimpleJdbcInsert medScheduleInserter;


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
        this.medScheduleInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("MedicationSchedule")
                .usingGeneratedKeyColumns("medSchedule_id");
    }


    private RowMapper<Customer> customerRowMapper() {
        return (rs, rowNum) -> new Customer(
                rs.getInt("customer_id"),
                rs.getString("customer_name"),
                rs.getDate("birthDate").toLocalDate(),
                rs.getString("email"),
                rs.getBoolean("hasCareGiver"),
                rs.getString("password")
                // TODO: Handle other properties if needed
        );
    }

    @Override
    public Customer createCustomer(Customer customer) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("customer_id", customer.getCustomer_id());
        parameters.put("customer_name", customer.getCustomer_name());
        parameters.put("birthDate", customer.getBirthDate());
        parameters.put("email", customer.getEmail());
        parameters.put("hasCareGiver", customer.getCareGivers());
        customer.setCustomer_id(customerInserter.executeAndReturnKey(parameters).intValue());
        logger.info("Creating customer {}", customer);

        return customer;
    }

    @Override
    public List<Customer> findAllCustomers() {
        return jdbcTemplate.query("SELECT * FROM Customer", customerRowMapper());
    }

    @Override
    public Customer findCustomerById(int customer_id) {
        return (Customer) jdbcTemplate.query("SELECT * FROM Customer where customer_id = ? ",
                customerRowMapper(), customer_id);
    }

    @Override
    public Customer findCustomerByEmail(String email) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM Customer WHERE email = ?",
                    new Object[]{email},
                    customerRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return null; // No customer found with the given email
        }
    }

    @Override
    public Customer updateCustomer(Customer existingCustomer) {
        String updateQuery = "UPDATE Customer SET customer_name = ?, birthDate = ?, email = ?, hasCareGiver = ?, password = ? WHERE customer_id = ?";

        int updatedRows = jdbcTemplate.update(updateQuery,
                existingCustomer.getCustomer_name(),
                existingCustomer.getBirthDate(),
                existingCustomer.getEmail(),
                existingCustomer.isHasCareGiver(),
                existingCustomer.getCustomer_id(),
                existingCustomer.getPassword());

        if (updatedRows > 0) {
            logger.info("Customer updated: {}", existingCustomer);
            return existingCustomer;
        } else {
            logger.warn("Failed to update customer with ID: {}", existingCustomer.getCustomer_id());
            return null;
        }
    }

    @Override
    public Customer deleteCustomer(int customer_id) {
        String selectQuery = "SELECT * FROM Customer WHERE customer_id = ?";
        String deleteQuery = "DELETE FROM Customer WHERE customer_id = ?";

        Customer deletedCustomer = jdbcTemplate.queryForObject(selectQuery, customerRowMapper(), customer_id);

        int deletedRows = jdbcTemplate.update(deleteQuery, customer_id);

        if (deletedRows > 0) {
            logger.info("Customer with ID {} deleted successfully", customer_id);
            return deletedCustomer;
        } else {
            logger.warn("Failed to delete customer with ID: {}", customer_id);
            return null;
        }
    }


    public static Dashboard dashboardRow(ResultSet rs, int rowid) throws SQLException {
        int dashboardId = rs.getInt("dashboard_id");
        int nrPillTaken = rs.getInt("nrPillTaken");
        int duration = rs.getInt("duration");
        int customerId = rs.getInt("customer_id");

        // Assuming Customer class has a constructor that takes an int (customer_id)
        Customer customer = new Customer(customerId);

        return new Dashboard(dashboardId, nrPillTaken, duration, customer);
    }



    @Override
    public Dashboard createDashboard(Dashboard dashboard){
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("dashboard_id", dashboard.getDashboard_id());
        parameters.put("nrPillTaken", dashboard.getNrPillTaken());
        parameters.put("duration", dashboard.getDuration());
        parameters.put("customer_id", dashboard.getCustomer().getCustomer_id());
        dashboard.setDashboard_id(dashboardInserter.executeAndReturnKey(parameters).intValue());
        logger.info("Creating a dashboard {}", dashboard);
        return dashboard;

    }

    @Override
    public List<Dashboard> findAllDashboards() {
        logger.info("Finding dashboards from the database...");
        List<Dashboard> dashboards = jdbcTemplate.query(
                "SELECT * FROM Dashboard", JDBCUserRepository::dashboardRow);
        return dashboards;
    }

    @Override
    public Dashboard findDashboardByID(int dashboard_ID) {
        Dashboard dashboard = jdbcTemplate.queryForObject("SELECT * FROM Dashboard WHERE dashboard_id = ?",
                JDBCUserRepository::dashboardRow, dashboard_ID);
        return dashboard;
    }

    @Override
    public Dashboard updateDashboard(Dashboard dashboard) {
        logger.info("Dashboard is updated {}", dashboard);
        jdbcTemplate.update("UPDATE Dashboard SET dashboard_ID = ? WHERE CUSTOMER_ID= ?",
                dashboard.getDashboard_id(), dashboard.getCustomer().getCustomer_id());
        return dashboard;
    }

    @Override
    public Dashboard deleteDashboard(int dashboard_ID) {
        Dashboard deletedDashboard = findDashboardByID(dashboard_ID);

        jdbcTemplate.update("DELETE FROM DASHBOARD WHERE dashboard_ID = ?", dashboard_ID);
        logger.info("Dashboard with id {} is deleted ", dashboard_ID);

        return deletedDashboard;
    }



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
    public List<CareGiver> findAllCareGivers() {
        return jdbcTemplate.query("SELECT * FROM CareGiver", CAREGIVER_ROW_MAPPER);
    }

    @Override
    public CareGiver findCaregiverByID(int caregiver_id) {
        return jdbcTemplate.queryForObject("SELECT * FROM CareGiver WHERE caregiver_id = ?",
                new Object[]{caregiver_id}, CAREGIVER_ROW_MAPPER);
    }


    @Override
    public CareGiver updateCareGiver(CareGiver careGiver) {
        int updatedRows = jdbcTemplate.update(
                "UPDATE CareGiver SET caregiver_name = ?, email = ? WHERE caregiver_id = ?",
                careGiver.getCaregiver_name(), careGiver.getEmail(), careGiver.getCaregiver_id()
        );

        if (updatedRows > 0) {
            logger.info("CareGiver with ID {} updated successfully", careGiver.getCaregiver_id());
            return careGiver;
        } else {
            logger.warn("Failed to update CareGiver with ID: {}", careGiver.getCaregiver_id());
            return null;
        }
    }

    @Override
    public CareGiver deleteCaregiver(int caregiverId) {
        String selectQuery = "SELECT * FROM CareGiver WHERE caregiver_id = ?";
        CareGiver deletedCaregiver = jdbcTemplate.queryForObject(
                selectQuery,
                CAREGIVER_ROW_MAPPER,
                caregiverId
        );

        int deletedRows = jdbcTemplate.update(
                "DELETE FROM CareGiver WHERE caregiver_id = ?",
                caregiverId
        );

        if (deletedRows > 0) {
            logger.info("CareGiver with ID {} deleted successfully", caregiverId);
            return deletedCaregiver;
        } else {
            logger.warn("Failed to delete CareGiver with ID: {}", caregiverId);
            return null;
        }
    }


    private static final RowMapper<MedicationSchedule> MEDICATION_SCHEDULE_ROW_MAPPER = (rs, rowNum) -> {
        MedicationSchedule medicationSchedule = new MedicationSchedule();
        medicationSchedule.setMedSchedule_id(rs.getInt("medSchedule_id"));
        medicationSchedule.setCustomer_id(rs.getInt("customer_id"));
        medicationSchedule.setStartDate(rs.getDate("startDate").toLocalDate());
        medicationSchedule.setEndDate(rs.getDate("endDate").toLocalDate());
        medicationSchedule.setPillName(rs.getString("pillName"));
        medicationSchedule.setQuantity(rs.getInt("quantity"));
        medicationSchedule.setTimeTakePill(rs.getDate("timeTakePill").toLocalDate());
        // Set other properties as needed
        return medicationSchedule;
    };

    @Override
    public List<MedicationSchedule> findAllMedSchedules() {
        return jdbcTemplate.query("SELECT * FROM MedicationSchedule", MEDICATION_SCHEDULE_ROW_MAPPER);
    }

    @Override
    public MedicationSchedule findMedScheduleById(int medSchedule_id) {
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
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("customer_id", medSchedule.getCustomer_id());
        parameters.put("startDate", medSchedule.getStartDate());
        parameters.put("endDate", medSchedule.getEndDate());
        parameters.put("pillName", medSchedule.getPillName());
        parameters.put("quantity", medSchedule.getQuantity());
        parameters.put("timeTakePill", medSchedule.getTimeTakePill());

        Number newId = medScheduleInserter.executeAndReturnKey(parameters);
        medSchedule.setMedSchedule_id(newId.intValue());
        return medSchedule;
    }

    @Override
    public MedicationSchedule updateMedSchedule(MedicationSchedule medicationSchedule) {
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





//counter of how many pills taken, each time pill taken measuremtn kept, keep measurement all the time


}
