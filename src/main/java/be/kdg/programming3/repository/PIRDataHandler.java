package be.kdg.programming3.repository;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PIRDataHandler {
    private final PIRFileWriter fileWriterCSV = new PIRFileWriter();
    private StringBuilder pirDataSb = new StringBuilder();
    private final Logger logger = LoggerFactory.getLogger(PIRDataHandler.class);
    private Scanner reader = new Scanner(System.in);
    private SerialPort activePort;

    private SerialPort[] ports = SerialPort.getCommPorts();
    private Date previousDate = new Date();

    /**
     * This method shows all available serial ports.
     */
    public void showAllPorts() {
        int i = 0;
        for (SerialPort port : ports) {
            System.out.print(i + ". " + port.getDescriptivePortName() + " ");
            System.out.println(port.getPortDescription());
            i++;
        }
    }

    /**
     * This method will notify the user if the chosen port (connected to the Arduino) is successfully opened
     *
     * @param portIndex the port to which your Arduino is connected
     */

    private void setPort(int portIndex) {
        activePort = ports[portIndex];
        if (activePort.openPort()) {
            logger.info("{} port opened.", activePort.getPortDescription());
        } else {
            logger.info("{} port NOT opened.", activePort.getPortDescription());
        }
    }

    /**
     * This method starts the application and runs all separate components
     * - shows the available ports
     * - choose the port your Arduino is connected to
     * - while the program runs, we will write our data from serial to a CSV file at one-second intervals
     *
     * @throws IOException
     */
    public void start() throws IOException {
        showAllPorts();
        System.out.print("Port? ");
        int p = reader.nextInt();
        setPort(p);
        while (true) {
            Date currentDate = new Date();
            if (!isSameSecond(previousDate, currentDate)) {
                logger.info("ONE SECOND PASSED");
                previousDate = currentDate; // Update the last observed date
                handleData();
                fileWriterCSV.write(pirDataSb.toString());
                previousDate = new Date();
                pirDataSb.setLength(0);
            }
        }
    }

    /**
     * This method listens in on our serial port and appends all the incoming data to a StringBuilder.
     */
    public void handleData() {
        activePort.addDataListener(new SerialPortDataListener() {
            @Override
            public void serialEvent(SerialPortEvent event) {
                int size = event.getSerialPort().bytesAvailable();
                byte[] buffer = new byte[size];
                event.getSerialPort().readBytes(buffer, size);
                String receivedData = new String(buffer).trim();

                // Process the received data
                if (receivedData.equals("Motion detected")) {
                    // Handle motion detection event
                    logger.info("Motion detected");
                } else if (receivedData.equals("Motion ended")) {
                    // Handle motion ended event
                    logger.info("Motion ended");
                }
            }

            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }
        });
    }

    /**
     * This method checks when exactly one second has passed.
     *
     * @param previousDate the previous date (time) -> "yyyy-MM-dd HH:mm:ss".
     * @param currentDate  The current date (time).
     * @return boolean.
     */
    private static boolean isSameSecond(Date previousDate, Date currentDate) {
        SimpleDateFormat secondFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String second1 = secondFormat.format(previousDate);
        String second2 = secondFormat.format(currentDate);
        return second1.equals(second2);
    }
}







//package be.kdg.programming3.repository;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Scanner;
//import com.fazecast.jSerialComm.SerialPort;
//import com.fazecast.jSerialComm.SerialPortDataListener;
//import com.fazecast.jSerialComm.SerialPortEvent;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//    /**
//     * This class gets the data out of the arduino through the serial port. It uses a FileWriterCSV object to write the data to a file every second passed.
//     * The first two methods deal with the port selection.
//     * The main methods are start() and handleData().
//     *     -> These are used for reading the data and appending it to a StringBuilder object.
//     * The last method checks when a second  passed.
//     */
//
//
//    public class PIRDataHandler {
//        private final FileWriter fileWriterCSV = new FileWriter();
//        private StringBuilder pirDataSb = new StringBuilder();
//        private final Logger logger = LoggerFactory.getLogger(PIRDataHandler.class);
//        Scanner reader = new Scanner(System.in);
//        SerialPort activePort;
//        SerialPort[] ports = SerialPort.getCommPorts();
//        Date previousDate = new Date();
//        /**
//         * This method shows all available serial ports.
//         */
//        public void showAllPorts() {
//            int i = 0;
//            for (SerialPort port : ports) {
//                System.out.print(i + ". " + port.getDescriptivePortName() + " ");
//                System.out.println(port.getPortDescription());
//                i++;
//            }
//        }
//        /**
//         * This method will notify the user if the chosen port (connected to the arduino) is successfully opened
//         *
//         * @param portIndex the port to which your arduino is connected
//         */
//        private void setPort(int portIndex) {
//            activePort = ports[portIndex];
//            if (activePort.openPort()) {
//                logger.info("{} port opened.", activePort.getPortDescription());
//            } else {
//                logger.info("{} port NOT opened.", activePort.getPortDescription());
//            }
//        }
//        /**
//         *This is method will start the application and run all separate components
//         * - shows the available ports
//         * - choose the port your arduino is connected to
//         * - while the program runs we will write our data from serial to a CSV file at one second intervals
//         *
//         * @throws IOException
//         */
//        public void start() throws IOException {
//            showAllPorts();
//            System.out.print("Port? ");
//            int p = reader.nextInt();
//            setPort(p);
//            while (true) {
//                Date currentDate = new Date();
//                if (!isSameSecond(previousDate, currentDate)) { // compare if date has changed since the last iteration (we check for every second difference)
//                    logger.info("ONE SECOND PASSED");
//                    previousDate = currentDate; // Update the last observed date
//                    handleData();
//                    fileWriterCSV.write(pirDataSb.toString());
//                    previousDate = new Date();
//                    pirDataSb.setLength(0);
//                }
//            }
//        }
//
//
//
//        /**
//         * This method listens in on our serial port and appends all the incoming data to a StringBuilder.
//         */
//
//        public void handleData() {
//            activePort.addDataListener(new SerialPortDataListener(
//
//                @Override
//                public void serialEvent(SerialPortEvent event) {
//                    int size = event.getSerialPort().bytesAvailable();
//                    byte[] buffer = new byte[size];
//                    event.getSerialPort().readBytes(buffer, size);
//                    String receivedData = new String(buffer).trim();
//
//                    // Process the received data
//                    if (receivedData.equals("Motion detected")) {
//                        // Handle motion detection event
//                        logger.info("Motion detected");
//                    } else if (receivedData.equals("Motion ended")) {
//                        // Handle motion ended event
//                        logger.info("Motion ended");
//                    }
//                }
//
//            @Override
//            public int getListeningEvents() {
//                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
//            }
//
//
//
//
//
////
////        public void handleData() {
////            activePort.addDataListener(new SerialPortDataListener() {
////                                                           @Override
////                public void serialEvent(SerialPortEvent event) {
////                    int size = event.getSerialPort().bytesAvailable();
////                    byte[] buffer = new byte[size];
////                    event.getSerialPort().readBytes(buffer,size);
////                    String receivedData = new String(buffer).trim(); // Convert bytes to a String
////                    brainDataSb.append(receivedData);
////                    //TODO uncomment to see brainData printed
////                    //System.out.println("brainDataSb: " + brainDataSb);
////                }
////                                           @Override
////                                           public int getListeningEvents() {
////                                               return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
////                                           }
////
////                                       }
//
//                                           /**
//                                            * This method checks when exactly one second has passed.
//                                            *
//                                            * @param previousDate the previous date (time) -> "yyyy-MM-dd HH:mm:ss".
//                                            * @param currentDate  The current date (time).
//                                            * @return boolean.
//                                            */
//                                           private static boolean isSameSecond(Date previousDate, Date currentDate) {
//                                               SimpleDateFormat secondFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                                               String second1 = secondFormat.format(previousDate);
//                                               String second2 = secondFormat.format(currentDate);
//                                               return second1.equals(second2);
//                                           }
//                                       }
//
//
//

//Arduino code

///** #include <Arduino.h>
//
// // Pin assignments
// const int pirPin = 3;    // the digital pin connected to the PIR sensor's output
// const int ledPin = 13;   // the digital pin connected to the LED
//
// // Timing variables
// const int calibrationTime = 30; // time for sensor calibration (seconds)
// unsigned long lowIn;            // time when the sensor outputs a low impulse
// const unsigned long pause = 5000; // time in milliseconds the sensor has to be low before we assume all motion has stopped
//
// // State variables
// bool lockLow = true;
// bool takeLowTime;
//
// void setup() {
// Serial.begin(9600);
// pinMode(pirPin, INPUT);
// pinMode(ledPin, OUTPUT);
// digitalWrite(pirPin, LOW);
//
// // Calibrate the sensor
// Serial.print("Calibrating sensor ");
// for (int i = 0; i < calibrationTime; i++) {
// Serial.print(".");
// delay(1000);
// }
// Serial.println(" done");
// Serial.println("SENSOR ACTIVE");
// delay(50);
// }
//
// void loop() {
// if (digitalRead(pirPin) == HIGH) {
// digitalWrite(ledPin, HIGH); // Turn on LED when motion is detected
// if (lockLow) {
// // Ensures we wait for a transition to LOW before further output
// lockLow = false;
// Serial.println("---");
// Serial.print("Motion detected at ");
// Serial.print(millis() / 1000);
// Serial.println(" sec");
// delay(50);
// }
// takeLowTime = true;
// }
//
// if (digitalRead(pirPin) == LOW) {
// digitalWrite(ledPin, LOW); // Turn off LED when no motion is detected
//
// if (takeLowTime) {
// lowIn = millis(); // Save the time of transition from high to LOW
// takeLowTime = false; // Make sure this is only done at the start of a LOW phase
// }
//
// // If the sensor is low for more than the given pause,
// // we assume that no more motion is going to happen
// if (!lockLow && millis() - lowIn > pause) {
// // Ensures this block of code is executed again only after a new motion sequence has been detected
// lockLow = true;
// Serial.print("Motion ended at ");
// Serial.print((millis() - pause) / 1000);
// Serial.println(" sec");
// delay(50);
// }
// }
// }
//**/


