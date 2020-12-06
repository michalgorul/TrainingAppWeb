

function calculateBmi(arg1, arg2, result){

    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function(){

        if(this.readyState == 4 && this.status == 200){

            document.getElementById(result).innerHTML = this.responseText;
        }
    };

    let ar1 = document.getElementById(arg1).value;
    let ar2 = document.getElementById(arg2).value;

    xhttp.open("GET", "bmi?arg1=" + ar1 + "&arg2=" + ar2, true);
    xhttp.send();

}

function saveExercise(category, comment, date, distance, duration, result){

    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function(){

        if(this.readyState == 4 && this.status == 200){

            document.getElementById(result).innerHTML = this.responseText;
        }
    };

    let combo = document.getElementById(category)

    let ar1 = combo.options[combo.selectedIndex].text;
    let ar2 = document.getElementById(comment).value;
    let ar3 = document.getElementById(date).value;
    let ar4 = document.getElementById(distance).value;
    let ar5 = document.getElementById(duration).value;

    xhttp.open("GET",
        "exercise?category=" + ar1
        + "&comment=" + ar2
        + "&date=" + ar3
        + "&distance=" + ar4
        + "&duration=" + ar5, true);

    xhttp.send();

}
function updateTable(result){

    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function(){

        if(this.readyState == 4 && this.status == 200){

            document.getElementById(result).innerHTML = this.responseText;
        }
    };
    xhttp.send();

}

function goForMainPage() {
    window.location.href="../mainPage.html";
}

function goForExercise() {
    window.location.href="http://localhost:8080/exercise.html";
}

function goForBmi() {
    window.location.href="http://localhost:8080/bmi.html";
}

function goForStats() {
    window.location.href="http://localhost:8080/stats.html";
}

function goForHistory() {
    window.location.href="http://localhost:8080/history.html";
}