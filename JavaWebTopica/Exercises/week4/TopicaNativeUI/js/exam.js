var hoursContainer = document.querySelector('.hours')
var minutesContainer = document.querySelector('.minutes')
var secondsContainer = document.querySelector('.seconds')
var tickElements = Array.from(document.querySelectorAll('.tick'))
var time = 0;
var last = new Date(time)
last.setUTCHours(-1)

var tickState = true

function updateTime(newDate) {
    time += 1000;
    var now = new Date(time);
    now.setUTCHours(-1);

    var lastHours = last.getHours().toString()
    var nowHours = now.getHours().toString()
    if (lastHours !== nowHours) {
        updateContainer(hoursContainer, nowHours)
    }

    var lastMinutes = last.getMinutes().toString()
    var nowMinutes = now.getMinutes().toString()
    if (lastMinutes !== nowMinutes) {
        updateContainer(minutesContainer, nowMinutes)
    }

    var lastSeconds = last.getSeconds().toString()
    var nowSeconds = now.getSeconds().toString()
    if (lastSeconds !== nowSeconds) {
        //tick()
        updateContainer(secondsContainer, nowSeconds)
    }

    last = now
}

function tick() {
    tickElements.forEach(t => t.classList.toggle('tick-hidden'))
}

function updateContainer(container, newTime) {
    var time = newTime.split('')

    if (time.length === 1) {
        time.unshift('0')
    }


    var first = container.firstElementChild
    if (first.lastElementChild.textContent !== time[0]) {
        updateNumber(first, time[0])
    }

    var last = container.lastElementChild
    if (last.lastElementChild.textContent !== time[1]) {
        updateNumber(last, time[1])
    }
}

function updateNumber(element, number) {
    //element.lastElementChild.textContent = number
    var second = element.lastElementChild.cloneNode(true)
    second.textContent = number

    element.appendChild(second)
    element.classList.add('move')

    setTimeout(function () {
        element.classList.remove('move')
    }, 990)
    setTimeout(function () {
        element.removeChild(element.firstElementChild)
    }, 990)
}

setInterval(updateTime, 1000)

function renderProgressBar(percentage, callback) {
    $("#my-progress-bar").css("width", percentage + "%");

    $("#my-progress-tooltip-box").css("margin-left", percentage - 2.4 + "vw");
    $("#my-progress-tooltip").html("" + (20 * percentage / 100) + "/20");
    setTimeout(callback, 1000);
}

$(document).ready(function () {
    $(".choice").hover(function () {
        $(this).toggleClass("active");
    });

    $("#btnNext").click(function () {
        renderProgressBar(100, function () {
            window.location.href = "./congratulation.html?t=" + time;
        });
    });
    $("#btnBack").click(function () {
        renderProgressBar(0, function () {
            window.location.href = "./homepage.html";
        })
    });
    renderProgressBar(75, function () { });
});
