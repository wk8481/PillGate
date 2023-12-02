import {usersJson} from "./users";

const users = JSON.parse(usersJson);

const loginForm = document.getElementById("login-form");
const loginButton = document.getElementById("login-btn");

loginButton.addEventListener("click", e => {
    e.preventDefault();
    const username = loginForm.username.value;
    const password = loginForm.password.value;
    const user = users.find((user) =>
        user.username === username &&
        user.password === password);

    if (user) {
        alert("You have successfully logged in");
        const redirect = "dashboard.html?username=" + encodeURIComponent(username);
        window.location.href = redirect;
    } else {
        alert("Invalid username and/or password");
        window.location.reload();
    }

})
