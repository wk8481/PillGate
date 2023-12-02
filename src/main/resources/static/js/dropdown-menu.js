// scroll to section when clicking on a link
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
        e.preventDefault();

        document.querySelector(this.getAttribute('href')).scrollIntoView({
            behavior: 'smooth'
        });
    });
});


// hide logo on scroll
document.addEventListener('DOMContentLoaded', function() {
    let logoContainer = document.querySelector('.logo-container');

    window.addEventListener('scroll', function() {
        if (window.scrollY > 0) {
            logoContainer.classList.add('hide-logo');
        } else {
            logoContainer.classList.remove('hide-logo');
        }
    });
});

// show section2 after scrolling
window.addEventListener('scroll', function() {
    let logoHeight = document.getElementById('navbar').offsetHeight;
    let section2 = document.getElementById('section2');
    let scrollPosition = window.scrollY;

    let triggerPoint = logoHeight * 1.5;

    if (scrollPosition > triggerPoint) {
        section2.classList.add('visible-section');
        section2.classList.remove('hidden-section');
    } else {
        section2.classList.add('hidden-section');
        section2.classList.remove('visible-section');
    }
});


document.addEventListener('DOMContentLoaded', function() {
    window.addEventListener('scroll', function() {
        let scrollPosition = window.scrollY;

        // Adjust the value below to control when the main logo disappears
        let triggerPoint = document.querySelector('.logo-container').offsetHeight;

        // Toggle visibility of logos based on scroll position
        document.querySelector('.logo-container').style.display = scrollPosition > triggerPoint ? 'none' : 'block';
        document.querySelector('.smaller-logo').style.display = scrollPosition > triggerPoint ? 'block' : 'none';
    });
});