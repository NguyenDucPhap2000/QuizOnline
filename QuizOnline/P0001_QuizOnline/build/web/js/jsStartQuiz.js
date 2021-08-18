// timeEnd = current time in my laptop + time take quiz 
var timeEnd = document.getElementById("timeEnd").value;
var x = setInterval(function () {
    var now = new Date().getTime();
    // time to take quiz
    var timer = timeEnd - now;
    //convert miliseconds of take quiz time to minutes
    var minutes = Math.floor((timer % (1000 * 60 * 60)) / (1000 * 60));
    //convert miliseconds of take quiz time to seconds
    var seconds = Math.floor((timer % (1000 * 60)) / 1000);
    if(seconds < 10){
        document.getElementById("timer").innerHTML = minutes + ":" + "0" + seconds;
    }else{
        document.getElementById("timer").innerHTML = minutes + ":" + seconds;
    }
    
    //when time out of quiz access to ResultControl
    if (timer < 1) {
        document.getElementById("timer").innerHTML ="0:" + "00";
        clearInterval(x);
        window.location = "TakeQuizControl";
    }
}, 1000);
