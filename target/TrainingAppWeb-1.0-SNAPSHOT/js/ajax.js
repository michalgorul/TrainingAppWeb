

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
    update();
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