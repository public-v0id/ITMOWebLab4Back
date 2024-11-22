window.onload = function () {
    setTime();
    setInterval(setTime, 9000);
}

function setTime() {
    const time = new Date();
    const dateString = time.toLocaleDateString(undefined, { day: 'numeric', month: 'short', year: 'numeric' });
    const timeString = time.toLocaleTimeString(undefined, { hour: '2-digit', minute: '2-digit', second: '2-digit' });
    console.log(dateString + " " + timeString)
        document.getElementById("date").innerHTML = dateString;
        document.getElementById("time").innerHTML = timeString;
}