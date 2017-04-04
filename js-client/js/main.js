var PREFIX_URL = "http://localhost:8088";
var SESSION_URL = "/session";
var CUSTOMER_URL = "/customer";
var BOOKING_URL = "/booking";

getSessionsWithSeats();

function getSessionsWithSeats() {
    var url = PREFIX_URL + BOOKING_URL + "/getSessionsWithSeats";

    $.ajax({
        type: "GET",
        url: url,
        dataType: "json", // data type of response
        success: renderSessionsWithSeats,
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
            alert('getSessionsWithSeats: ' + textStatus);
        }
    });
}

function renderSessionsWithSeats(data) {
    data = data == null ? [] : (data instanceof Array ? data : [data]);

    $("#sessionWithSeatsList tr").remove();

    $.each(data, drawSessionWithSeatsRow);
}

function drawSessionWithSeatsRow(index, session) {
    var row = $("<tr />");
    $("#sessionWithSeatsList").append(row);

    row.append($("<td>" + (index + 1) + "</td>"));
    row.append($("<td>" + session.movieName + "</td>"));
    row.append($("<td>" + session.sessionDate + "</td>"));
    row.append($("<td>" + session.seats + "</td>"));
    row.append($("<td>" + session.totalSeats + "</td>"));
    row.append($(
        "<td>" +
            '<div class="btn-group">' +
                '<button class="btn btn-default" onclick="gotoSession(' + session.sessionId + ')">Edit</button>' +
                '<button class="btn btn-default" onclick="gotoCustomers(' + session.sessionId + ')">View</button>' +
                '<button class="btn btn-danger" onclick="deleteSession(' + session.sessionId + ')">Delete</button>' +
            '</div>' +
        '</td>'));
}

function gotoSession(id) {
    window.location = "viewSession.html?id=" + id;
}

function gotoCustomers(id) {
    window.location = "sessionCustomers.html?id=" + id;
}

function deleteSession(id) {
    if (confirm("Delete this session: " + id + "?")) {
        var url = PREFIX_URL + SESSION_URL + "/delete?id=" + id;

        $.ajax({
            type: "DELETE",
            url: url,
            success: function (data, textStatus, jqXHR) {
                alert('Session deleted successfully');
                getSessionsWithSeats();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert('delete error: ' + textStatus + id);
            }
        })
    }
}

$("#btnFilter").click(function () {
    var url = PREFIX_URL + BOOKING_URL + "/getSessionsWithSeats";

    $.ajax({
        type: "GET",
        url: url,
        dataType: "json",
        data: {
          "firstDate": $("#firstDate").val(),
          "secondDate": $("#secondDate").val(),
        },
        success: renderSessionsWithSeats,
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
            alert('getSessionsWithSeats: ' + textStatus);
        }
    });
});

$("#btnAdd").click(function () {
    window.location = "viewSession.html";
});