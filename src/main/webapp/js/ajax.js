

function calculateBmi(arg1, arg2, result){

    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function(){

        if(this.readyState == 4 && this.status == 200){

            document.getElementById(result).innerHTML = this.responseText;
        }
    };

    let ar1 = document.getElementById(arg1).value;
    let ar2 = document.getElementById(arg2).value;


    if(parseFloat(ar1) < 0 || parseFloat(ar1) > 320.0 || isNaN(ar1) || ar1.length === 0) {

        alert("Please enter right height");
    }

    else if(parseFloat(ar2) < 0 || parseFloat(ar2) > 320.0 || isNaN(ar2) || ar2.length === 0) {

        alert("Please enter right weight");
    }

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

    if(ar3.length === 0){
        alert("Please enter right date");
    }
    else if(parseFloat(ar4) < 0 || isNaN(ar4) || ar4.length === 0){
        alert("Please enter right distance");
    }
    else if(parseFloat(ar5) < 0 || isNaN(ar5) || ar5.length === 0){
        alert("Please enter right duration");
    }

    xhttp.open("GET",
        "exercise?category=" + ar1
        + "&comment=" + ar2
        + "&date=" + ar3
        + "&distance=" + ar4
        + "&duration=" + ar5, true);

    xhttp.send();

}

function getHistoryTable(){

    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById('historyTable').innerHTML = this.responseText;
        }
    };

    xhttp.open("GET", "HistoryServlet", true);
    xhttp.send();
}

function getBmiTable(){

    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById('bmiTable').innerHTML = this.responseText;
        }
    };

    xhttp.open("GET", "BmiHistoryServlet", true);
    xhttp.send();
}

function getBmiHeight(){

    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById('lastHeight').innerHTML = this.responseText;
        }
    };

    xhttp.open("GET", "BmiServlet", true);
    xhttp.send();
}

function getBmiWeight(){

    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById('lastWeight').innerHTML = this.responseText;
        }
    };

    xhttp.open("GET", "BmiServlet", true);
    xhttp.send();
}

function getStatsTable(){

    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById('statsTable').innerHTML = this.responseText;
        }
    };

    xhttp.open("GET", "StatsServlet", true);
    xhttp.send();
}

function clearHistory(){

    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById('clearing').innerHTML = this.responseText;
        }
    };

    alert("History cleared. ");
    xhttp.open("GET", "ClearHistoryServlet", true);
    xhttp.send();
    location.reload();
}

function clearBmiHistory(){

    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById('clearingBmi').innerHTML = this.responseText;
        }
    };

    alert("History cleared. ");
    xhttp.open("GET", "ClearBmiHistoryServlet", true);
    xhttp.send();
    location.reload();
}

function getCategories(){

    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById('category').innerHTML = this.responseText;
        }
    };

    xhttp.open("GET", "exerciseNames", true);
    xhttp.send();
}

function update() {

    let table = document.getElementById('historyTable'), index;
    for(var i = 0; i < table.rows.length; i++) {
        table.rows[i].onClick = function() {
            index = this.rowIndex;
            console.log(index);
        }
    }
}

function start(){

    getHistoryTable();
    getStatsTable();
    getBmiTable();
}

function lastBmi(){
    getBmiHeight();
    getBmiWeight();
}

function goForMainPage() {
    window.location.href="../mainPage.html";
}

function goForExercise() {
    window.location.href="../exercise.html";
}

function goForBmi() {
    window.location.href="../bmi.html";
}

function goForStats() {
    window.location.href="../stats.html";
}

function goForHistory() {
    window.location.href="../history.html";
}