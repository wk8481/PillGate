// Define a flag to track whether the alert is currently shown
let isAlartShown = false;

const repeatedFunction = () => {
    console.log("Calling backend...");
    fetch("http://localhost:8080/alarm/now")
        .then((response) => {
            if(!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`)
            }
            return response.json();
        })
        .then((data) => {
            if (data.message != null) {
                swal.fire({
                    title: "Alarm",
                    text: data.message,
                    icon:"info",
                    button:"Stop"
                }).then((result) => {
                    clearInterval(interval);

                });
            }
        })
        .catch((error) => {
            console.error('Fetch error: ', error);
        });
};


let interval = setInterval(repeatedFunction, 5000);//check every 5 seconds if there is an alarm...



const repeatedFunction2 = () => {
    console.log("Calling backend...");
    fetch("http://localhost:8080/alarm/repeat")
        .then((response) => {
            if(!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`)
            }
            return response.json();
        })
        .then((data) => {
            if (data.message != null) {
                swal.fire({
                    title: "Alarm",
                    text: data.message,
                    icon:"info",
                    button:"Stop"
                }).then((result) => {
                    clearInterval(intervalForRepeat);

                });
            }
        })
        .catch((error) => {
            console.error('Fetch error: ', error);
        });
};

let intervalForRepeat = setInterval(repeatedFunction2, 10000);