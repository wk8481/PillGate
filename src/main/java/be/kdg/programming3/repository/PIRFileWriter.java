<<<<<<< HEAD
package be.kdg.programming3.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PIRFileWriter {

    private static final String CSV_HEADER =
            "motionDetected, motionEnded";
    private static final String CSV_FILE_NAME = "pillData.csv";
    private final Logger logger = LoggerFactory.getLogger(PIRFileWriter.class);

    /*This method will:
    *  - Write a static header for the date and 8 data points of the brain data
    *  - Append the current date with timestamp to every new line to the brainData.csv
    *   @param receivedData a complete string of brain data (all 8 data points)      */

    public void write(String receivedData) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_NAME, true)))
        {
            if (new File(CSV_FILE_NAME).length() == 0) {
                //Write the CSV header (maps to columns on a database)
                writer.write(CSV_HEADER);
                writer.newLine();
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // We create the current date in the right format

            Date timestamp = new Date(); // Use the current timestamp
            String csvLine = dateFormat.format(timestamp) + "," + receivedData;
            // Append the date and the incoming line of data

            writer.write(csvLine);
            writer.newLine();
            logger.info("Data appended to CSV file: " + CSV_FILE_NAME);
        }
        catch(IOException e) {
                e.printStackTrace();
            }
    }


}
=======
package be.kdg.programming3.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PIRFileWriter {

    private static final String CSV_HEADER =
            "motionDetected, motionEnded";
    private static final String CSV_FILE_NAME = "pillData.csv";
    private final Logger logger = LoggerFactory.getLogger(PIRFileWriter.class);

    /*This method will:
    *  - Write a static header for the date and 8 data points of the brain data
    *  - Append the current date with timestamp to every new line to the brainData.csv
    *   @param receivedData a complete string of brain data (all 8 data points)      */

    public void write(String receivedData) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_NAME, true)))
        {
            if (new File(CSV_FILE_NAME).length() == 0) {
                //Write the CSV header (maps to columns on a database)
                writer.write(CSV_HEADER);
                writer.newLine();
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // We create the current date in the right format

            Date timestamp = new Date(); // Use the current timestamp
            String csvLine = dateFormat.format(timestamp) + "," + receivedData;
            // Append the date and the incoming line of data

            writer.write(csvLine);
            writer.newLine();
            logger.info("Data appended to CSV file: " + CSV_FILE_NAME);
        }
        catch(IOException e) {
                e.printStackTrace();
            }
    }


}
>>>>>>> master
