package be.kdg.programming3.repository;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

@Component
public class LSDataHandler {
    private final LSFileWriter fileWriterCSV = new LSFileWriter();
    private StringBuilder pirDataSb = new StringBuilder();
    private final Logger logger = LoggerFactory.getLogger(LSDataHandler.class);
    private Scanner reader = new Scanner(System.in);
    private SerialPort activePort;
    private SerialPort[] ports = SerialPort.getCommPorts();
    private Date previousDate = new Date();

    public void showAllPorts() {
        int i = 0;
        for (SerialPort port : ports) {
            System.out.print(i + ". " + port.getDescriptivePortName() + " ");
            System.out.println(port.getPortDescription());
            i++;
        }
    }

    private void setPort(int portIndex) {
        activePort = ports[portIndex];
        if (activePort.openPort()) {
            logger.info("{} port opened.", activePort.getPortDescription());
        } else {
            logger.info("{} port NOT opened.", activePort.getPortDescription());
        }
    }
    public void start() throws IOException {
        showAllPorts();
        System.out.print("Port? ");
        int p = reader.nextInt();
        setPort(p);
        setupDataListener();

        while (true) {
            Date currentDate = new Date();
            if (!isSameSecond(previousDate, currentDate)) {
                logger.info("ONE SECOND PASSED");
                previousDate = currentDate; // Update the last observed date
                fileWriterCSV.write(pirDataSb.toString());
                pirDataSb.setLength(0); // Clear the StringBuilder for the next set of data
                previousDate = new Date();
            }
        }
    }

    private void setupDataListener() {
        activePort.addDataListener(new SerialPortDataListener() {
            @Override
            public void serialEvent(SerialPortEvent event) {
                int size = event.getSerialPort().bytesAvailable();
                byte[] buffer = new byte[size];
                event.getSerialPort().readBytes(buffer, size);
                String receivedData = new String(buffer).trim();

                if (receivedData.equals("Motion detected") || receivedData.equals("Motion ended")) {
                    handleData(receivedData);
                    logger.info(receivedData);
                }
            }

            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }
        });
    }

    public void handleData(String sensorData) {
        pirDataSb.append(sensorData);
        pirDataSb.append(System.lineSeparator()); // Add a new line for each data entry
    }

    private static boolean isSameSecond(Date previousDate, Date currentDate) {
        SimpleDateFormat secondFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String second1 = secondFormat.format(previousDate);
        String second2 = secondFormat.format(currentDate);
        return second1.equals(second2);
    }
}
