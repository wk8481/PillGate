// use alert windows
// const repeatedFunction = () => {
//     console.log("Calling backend...");
//     fetch("http://localhost:8080/alarm/now")
//         .then((response) => {
//         if(!response.ok) {
//             throw new Error(`HTTP error! Status: ${response.status}`)
//         }
//             return response.json();
//         })
//         .then((data) => {
//             if (data.message != null) {
//                 alert("Alarm: " + data.message);
//             }
//         })
//         .catch((error) => {
//             console.error('Fetch error: ', error);
//         });
// };

// use swal2

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
                    // clearInterval(interval);

                });
            }
        })
        .catch((error) => {
            console.error('Fetch error: ', error);
        });
};


setInterval(repeatedFunction, 5000);//check every 5 seconds if there is an alarm...











<<<<<<< HEAD







=======
>>>>>>> 9022180 (added html file)








// // subscribe to a websocket topic
// let socket = new WebSocket('/websocket');
// socket.onmessage = function (event){
//     let reminderMessage = event.data;
//     showAlert(remindeMessage);
// }
//
//
//
// function showAlert(message) {
//     // this shows pop up windows for reminder
//     alert(message);
// }
//
// function pollReminders() {
//     // Poll for new reminders every 60 seconds
//     setInterval(function () {
//         fetch('/pollReminders')
//             .then(response => {
//                 if (!response.ok) {
//                     throw new Error('Network response was not ok');
//                 }
//                 return response.json();
//             })
//             .then(data => {
//                 if (data && data.message) {
//                     showAlert('Received reminder: ' + data.message);
//                 } else {
//                     console.warn('Received unexpected data:', data);
//                 }
//             })
//             .catch(error => {
//                 console.error('Error polling reminders:', error.message);
//             });
//     }, 60000);
// }
//
// function pollReminders() {
//     // Poll for new reminders every 60 seconds
//     setInterval(function () {
//         fetch('/pollReminders')
//             .then(response => response.json())
//             .then(data => {
//                 if (data && data.message) {
//                     showAlert('Received reminder: ' + data.message);
//                 }
//             })
//             .catch(error => console.error('Error polling reminders: ', error));
//     }, 60000); // Corrected the interval to 60 seconds
// }

// // start polling when the page is loaded
// document.addEventListener('DOMContentLoaded', function () {
//     // call the pollReminders function when the DOM content is fully loaded
//     pollReminders();
// });





// function showAlert(message){
// // this shows pop up windows for reminder
//     alert(message);
// }
//
// function pollReminders(){
//
//     // Poll for new reminders every 60 seconds
//     setInterval(function(){
//         fetch('/pollReminders')
//             .then(responce => responce.json())
//             .then(data => {
//                 if (data && data.message ){
//                     showAlert('Received reminder: '+ data.message);
//                 }
//             })
//             .catch (error => console.error('Error polling reminders: ', error));
//         }, 6000);
// }
//
// // start polling when the page is loaded
// document.addEventListener('DOMContentLoaded', function(){
//     // call the pollReminders function when the DOM content is fully loaded
//     pollReminders();
// });

