var PREFIX_URL = "http://localhost:8088";
var SESSION_URL = "/session";
var CUSTOMER_URL = "/customer";
var BOOKING_URL = "/booking";
var sessionId = getUrlParameter('id');

init();

function init() {
    if(sessionId === undefined)
        return;

    var url = PREFIX_URL + SESSION_URL + "/getById?id=" + sessionId;

    $.ajax({
        type: "GET",
        url: url,
        dataType: "json",
        success: function (data) {
            $("#sessionId").val(data.sessionId);
            $("#movieName").val(data.movieName);
            $("#sessionDate").val(data.sessionDate);
            $("#totalSeats").val(data.totalSeats);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
            alert('ERROR: ' + jqXHR.responseText);
        }
    })
}

function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
}

$("#btnOk").click(function () {
    if(sessionId == "") {
        addSession();
    }
    else {
        updateSession();
    }

    window.location = "index.html";
});


$("#btnCancel").click(function () {
    window.location = "index.html";
});

function addSession() {
    $.ajax({
        type: "POST",
        contentType: 'application/json',
        url: PREFIX_URL + SESSION_URL + "/add",
        dataType: "json",
        data: ToJSON(),
        success: function (data, textStatus, jqXHR) {
            alert('Session created successfully');
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('Error: ' + jqXHR.responseText);
        }
    });
}


function updateSession() {
    $.ajax({
        type: "PUT",
        contentType: 'application/json',
        url: PREFIX_URL + SESSION_URL + "/update",
        dataType: "json",
        data: ToJSON(),
        success: function (data, textStatus, jqXHR) {
            alert('Session updated successfully');
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('Error: ' + jqXHR.responseText);
        }
    });
}

function ToJSON() {
    return JSON.stringify({
        "sessionId": sessionId,
        "movieName": $("#movieName").val(),
        "sessionDate": $("#sessionDate").val(),
        "totalSeats": $("#totalSeats").val(),
    });
}