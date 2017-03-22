var host = "http://localhost:8088";

$.dto = null;

getAllSessions();

function getAllSessions() {
    $.ajax({
        type: 'GET',
        url: host + "/booking/getSessionsWithSeats",
        dataType: "json",
        success: renderList,
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
            alert('ERROR: ' + jqXHR.responseText);
        }
    });
}

function renderList(data) {
    dto = data == null ? [] : (data instanceof Array ? data : [data]);
    $('#sessionList tr').remove();

    $.each(dto, function (index, session) {
        drawRow(index, session);
    });
}

function drawRow(index, session) {
    var row = $("<tr />");
    $("#sessionList").append(row);

    row.append($("<td>" + (index + 1) + "</td>"));
    row.append($("<td>" + session.movieName + "</td>"));
    row.append($("<td>" + session.sessionDate + "</td>"));
    row.append($("<td>" + session.seats + "</td>"));
    row.append($("<td>" + session.totalSeats + "</td>"));
    row.append($("<td>" + '<a href="#" data-id="' + session.sessionId + '">edit</a> ' +
        '<a href="sessionCustomers.html?id=' + session.sessionId + '" data-id="' + session.sessionId + '">view</a> ' +
        '<a href="#" data-id="' + session.sessionId + '">delete</a></td>'));
}

$(document).on("click", "a", function() {
    var action = $(this).text();
    var selectedSessionId = $(this).data("id");

    if (action == "delete") {
        deleteSession(selectedSessionId);
    }
    else if (action == "edit") {
        $.each(dto, function (index) {
            if (dto[index].sessionId == selectedSessionId) {
                $("#sessionId").val(selectedSessionId);
                $("#movieName").val(dto[index].movieName);
                $("#sessionDate").val(dto[index].sessionDate);
                $("#totalSeats").val(dto[index].totalSeats);
            }
        });
    }
});

function deleteSession(sessionId) {
    $.ajax({
        type: 'DELETE',
        contentType: 'application/json',
        url: host + '/session/delete?id=' + sessionId,
        success: function (data, textStatus, jqXHR) {
            alert('Session delete successfully');
            getAllSessions();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('Error: ' + jqXHR.responseText);
        }
    });
}

$('#btnFilter').click(function () {
    var firstDate = $('#firstDate').val();
    var secondDate = $('#secondDate').val();
    getFilterSessions(firstDate, secondDate);
});

function getFilterSessions(firstDate, secondDate) {
    $.ajax({
        type: 'GET',
        url: host + "/booking/getSessionsWithSeats?firstDate=" + firstDate + "&secondDate=" + secondDate,
        dataType: "json",
        success: renderList,
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
            alert('ERROR: ' + jqXHR.responseText);
        }
    });
}

$('#btnSessionClean').click(function () {
    $("#sessionId").val("");
    $("#movieName").val("");
    $("#sessionDate").val("");
    $("#totalSeats").val("");
});

$('#btnSessionSave').click(function () {
    if ($('#sessionId').val() == '')
        addSession();
    else
        updateSession();
    return false;
});

function updateSession() {
    $.ajax({
        type: 'PUT',
        contentType: 'application/json',
        url: host + "/session/update",
        data: formToJSON(),
        success: function (data, textStatus, jqXHR) {
            alert('Session updated successfully');
            getAllSessions();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('Error: ' + jqXHR.responseText);
        }
    });
}

function addSession() {
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: host + "/session/add",
        dataType: "json",
        data: formToJSON(),
        success: function (data, textStatus, jqXHR) {
            alert('Session created successfully');
            getAllSessions();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('Error: ' + jqXHR.responseText);
        }
    });
}


function formToJSON() {
    var sessionId = $('#sessionId').val();
    return JSON.stringify({
        "sessionId": sessionId == "" ? null : sessionId,
        "movieName": $('#movieName').val(),
        "sessionDate": $('#sessionDate').val(),
        "totalSeats": $('#totalSeats').val(),
    });
}