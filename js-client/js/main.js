var host = "http://localhost:8088";

$.dto = null;

getAllSessions();

$(document).on("click", "a", function() {
    var action = $(this).text();
    var selectedSessionId = $(this).data("id");
    if (action != "delete") {
        $.each(dto, function (index) {
            if (dto[index].sessionId == selectedSessionId) {
                $("#sessionId").val(selectedSessionId);
                $("#movieName").val(dto[index].movieName);
                $("#sessionDate").val(dto[index].sessionDate);
            }
        });
    } else {
        deleteSession(selectedSessionId);
    }
});

$('#btnClean').click(function () {
    $("#groupId").val("");
    $("#groupName").val("");
    $("#gradDate").val("");
});

$('#btnSave').click(function () {
    if ($('#sessionId').val() == '')
        addSession();
    else
        updateSession();
    return false;
});

$('#btnFilter').click(function () {
    var startDate = $('#fromDate').val();
    var endDate = $('#toDate').val();
    getFilterSessions(startDate, endDate);
});

function getFilterSessions(startDate, endDate) {
    $.ajax({
        type: 'GET',
        url: host + "/session/getAllWithTicketsDateToDate?firstDate=" + startDate + "&secondDate=" + endDate,
        dataType: "json", // data type of response
        success: renderList,
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
            alert('ERROR: ' + jqXHR.responseText);
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

function getAllSessions() {
    $.ajax({
        type: 'GET',
        url: host + "/session/getAllWithTickets",
        dataType: "json", // data type of response
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
        drawRow(session);
    });
}

function drawRow(session) {
    var row = $('<tr />'); //onclick="redirect(' + session.sessionId + ')"
    $("#sessionList").append(row);

    row.append($("<td>" + '<a href="#" data-id="' + session.sessionId + '">' + session.movieName + '</a></td>'));
    row.append($("<td>" + session.sessionDate + "</td>"));
    row.append($("<td>" + session.quantityTickets + "</td>"));

    row.append($("<td>" + '<a href=customers.html?id=' + session.sessionId + '>view</a> <a href="#" data-id="' + session.sessionId + '">delete</a></td>'));
}

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

function formToJSON() {
    var sessionId = $('#sessionId').val();
    return JSON.stringify({
        "sessionId": sessionId == "" ? null : sessionId,
        "movieName": $('#movieName').val(),
        "sessionDate" : $('#sessionDate').val()
    });
}

function redirect(id) {
    window.location.href = "customers.html?id=" + id;
}
