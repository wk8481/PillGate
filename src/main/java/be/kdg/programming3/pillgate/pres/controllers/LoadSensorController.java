package be.kdg.programming3.pillgate.pres.controllers;//package be.kdg.programming3.oldproj.controllers;

import be.kdg.programming3.pillgate.domain.sensor.WeightSensor;
import be.kdg.programming3.pillgate.domain.user.Customer;
import be.kdg.programming3.pillgate.domain.user.MedicationSchedule;
import be.kdg.programming3.pillgate.repo.medSchedRepo.MedScheduleRepository;
import be.kdg.programming3.pillgate.repo.medSchedRepo.PGMedScheduleRepository;
import be.kdg.programming3.pillgate.repo.sensorRepo.SensorRepository;
import be.kdg.programming3.pillgate.service.MedicationScheduleService;
import be.kdg.programming3.pillgate.service.ReminderService;
import be.kdg.programming3.pillgate.service.SerialReader;
import jakarta.servlet.http.HttpSession;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.Map;

/**
 * Controller responsible for handling requests related to the sensor data and Arduino communication.
 * It provides methods to display sensor data, read Arduino data, show the number of pills taken,
 * and create a new sensor instance.
 * @author Team PillGate
 */
@Controller
@RequestMapping("/loadSensor")
public class LoadSensorController {

    private final SerialReader serialReader;

    private final SensorRepository sensorRepository;
    private final MedScheduleRepository medscheduleRepository;
    private final MedicationScheduleService medicationScheduleService;
    private final ReminderService reminderService;
    private final Logger logger = LoggerFactory.getLogger(LoadSensorController.class);

    /**
     * Constructs a new LoadSensorController with the specified dependencies.
     *
     * @param serialReader          The service for reading data from the Arduino.
     * @param sensorRepository
     * @param medscheduleRepository
     * @param reminderService       The service for managing medication reminders.
     */


    public LoadSensorController(SerialReader serialReader, SensorRepository sensorRepository, MedScheduleRepository medscheduleRepository, MedicationScheduleService medicationScheduleService, ReminderService reminderService) {
        this.serialReader = serialReader;
        this.sensorRepository = sensorRepository;
        this.medscheduleRepository = medscheduleRepository;
        this.medicationScheduleService = medicationScheduleService;
        this.reminderService = reminderService;
    }

    @GetMapping()
    public String showSensor(Model model, HttpSession session) {
        // check if user is logged in
        Object isLoggedIn = session.getAttribute("isLoggedIn");
        if (isLoggedIn == null || !(isLoggedIn instanceof  Boolean) || ! (Boolean) isLoggedIn) {
            return "redirect:/login";
        }

        // check if user is authenticated
        Object authenticatedUser = session.getAttribute("authenticatedUser");
        if (authenticatedUser != null && authenticatedUser instanceof Customer) {
            generateCharts(model);
            logger.info("Showing load sensor data ..");
            model.addAttribute("sensors", sensorRepository.findAllWSensors());
            return "dashboard";
        } else {
            logger.info("Customer not authenticated. Session details: {}", session.getAttributeNames());
            return "redirect:/login";
        }
    }

    /**
     * Reads data from the Arduino and displays the updated sensor data on the dashboard.
     *
     * @param model   The Spring MVC model.
     * @param session The HTTP session.
     * @return The name of the view to render.
     */
    @GetMapping("/readArduino")
    public String readArduino(Model model, HttpSession session) {
        if (session.getAttribute("authenticatedUser") != null) {

            try {
                serialReader.readArduinoData("COM5");
                model.addAttribute("sensors", sensorRepository.findAllWSensors());
            } catch (Exception e) {
                logger.info("Error reading Arduino data", e);
            }
            return "dashboard";
        } else {
            logger.info("Customer not authenticated. Session details: {}", session.getAttributeNames());
        }
        return "dashboard";
    }

    /**
     * Shows the number of pills taken and related information on the dashboard.
     *
     * @param model   The Spring MVC model.
     * @param session The HTTP session.
     * @return The name of the view to render.
     */
    @GetMapping("/readArduino/showPillsTaken")
    public String showNumberOfPillsTaken(Model model, HttpSession session) {
        if (session.getAttribute("authenticatedUser") != null) {
            MedicationSchedule latestMedSchedule = reminderService.getLatestMedicationSchedule();
            logger.info("Getting latest medication schedule {}", latestMedSchedule);

            if (latestMedSchedule != null) {
                model.addAttribute("nrOfPillsTaken", latestMedSchedule.getNrOfPillsTaken());
                logger.info("Showing number of pills taken {}", latestMedSchedule.getNrOfPillsTaken());

                model.addAttribute("weightOfSinglePill", latestMedSchedule.getWeightOfSinglePill());
                logger.info("Weight of Single Pill: {}", latestMedSchedule.getWeightOfSinglePill());

                model.addAttribute("nrOfPillsPlaced", latestMedSchedule.getNrOfPillsPlaced());
                logger.info("Number of Pills Placed: {}", latestMedSchedule.getNrOfPillsPlaced());
            }
        } else {
            logger.info("Customer not authenticated. Session details: {}", session.getAttributeNames());
        }

        return "dashboard";
    }

    /**
     * Creates a new sensor instance and displays the updated sensor data on the dashboard.
     *
     * @param model The Spring MVC model.
     * @return The name of the view to render.
     */

    @GetMapping("/createSensor")
    public String createSensor(Model model) {
        WeightSensor newSensor = new WeightSensor(/* initialize with necessary values */);
        sensorRepository.createSensor(newSensor);
        model.addAttribute("sensors", sensorRepository.findAllWSensors());
        return "dashboard";
    }

    @GetMapping("/generateCharts")
    public String generateCharts(Model model) {
        // Fetch data for the charts
        List<PGMedScheduleRepository.DailyCount> pillsTakenSummary =medicationScheduleService.getPillsTakenPerDay(7);
        Map<Integer, Map<String, Integer>> pillsTakenPerHour = medicationScheduleService.getTimeOfDayPillData();


        // Generate and pass the chart URLs
        String pillsTakenChartUrl = generateChartUrl("Pills Taken Over Week", pillsTakenSummary);
        String timeOfDayChartUrl = GenerateChartUrl((Map<Integer, Map<String, Integer>>) pillsTakenPerHour);

        model.addAttribute("pillsTakenChartUrl", pillsTakenChartUrl);
        model.addAttribute("timeOfDayChartUrl", timeOfDayChartUrl);

        return "dashboard";
    }

    private String generateChartUrl(String seriesName, List<PGMedScheduleRepository.DailyCount> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (PGMedScheduleRepository.DailyCount count : data) {
            dataset.addValue(count.getCount(), seriesName, count.getDay().toString());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                seriesName, // chart title
                "Date", // category axis label
                "Number of Pills Taken", // value axis label
                dataset,
                PlotOrientation.VERTICAL, // orientation
                false, // include legend
                true, // tooltips
                false // urls
        );

        CategoryPlot plot = chart.getCategoryPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits()); // ensure y-axis only shows integer values

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ChartUtils.writeChartAsPNG(outputStream, chart, 800, 600);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "data:image/png;base64," + Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }

    public String GenerateChartUrl(Map<Integer, Map<String, Integer>> countsPerHourPerPill) {
        // Formatter for the hour labels
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        // Create a dataset for the chart
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Loop through each hour
        for (Integer hour : countsPerHourPerPill.keySet()) {
            // Format the hour for the label
            LocalTime time = LocalTime.of(hour, 0);
            String hourLabel = time.format(timeFormatter);

            // Loop through each pill taken during this hour
            for (Map.Entry<String, Integer> pillEntry : countsPerHourPerPill.get(hour).entrySet()) {
                String pillName = pillEntry.getKey();
                Integer count = pillEntry.getValue();

                // Add the count to the dataset with the pill name as the series
                dataset.addValue(count, pillName, hourLabel);
            }
        }

        // Generate the chart with series for each pill
        JFreeChart chart = ChartFactory.createBarChart(
                "Time of Day Overview", // Chart title
                "Hour of Day", // X-axis label
                "Number of Pills Taken", // Y-axis label
                dataset, // Dataset
                PlotOrientation.VERTICAL,
                true, // Include legend to differentiate pills
                true, // Tooltips
                false // URLs
        );

        // Set the range axis to only display integer values
        CategoryPlot plot = chart.getCategoryPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // Convert the chart to a PNG image encoded as a Base64 string
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ChartUtils.writeChartAsPNG(outputStream, chart, 800, 600);
            return "data:image/png;base64," + Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }




}
