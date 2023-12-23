// Function to read the sensor
function readSensor() {
    // Use Thymeleaf syntax to get the URL for the readArduino endpoint
    // let readSensorUrl = "http://localhost:8080/serial/readSensor";
    // Make an AJAX request to the server


    fetch("serial/readSensor")
        .then(response => {
            if (response.ok) {
                console.log("Read sensor ok.");
            } else (
                console.error("Error reading sensor")
            )
        })
        .catch(error => console.error("Error: ", error))
}

// Function to disconnect the sensor
function disconnectSensor() {
    // Use Thymeleaf syntax to get the URL for the disconnect endpoint
    let disconnectSensorUrl = "http://localhost:8080/Serial/disconnectSensor";

    // // Make an AJAX request to the server
    fetch("/serial/disconnectSensor")
        .then(response => {
            if (response.ok) {
                console.log('disconnect success')
            } else {
                console.error('Error disconnecting sensor');
            }
        })
        .catch(error => console.error('Error', error));
}
    // $.get(disconnectSensorUrl, function(data) {
    //     // Handle the response if needed
    //     console.log("Sensor disconnected.");
    // });







