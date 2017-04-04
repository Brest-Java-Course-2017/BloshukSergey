var BOOKING_URL = "/booking";

function deleteBooking(sessionId, customerId) {
    var url = BOOKING_URL + "/delete?sessionId=" + sessionId + "&customerId=" + customerId;

    $.ajax({
        type: "DELETE",
        url: url,
        success: function (response) {
            document.open();
            document.write(response);
            document.close();
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
            alert('ERROR: ' + jqXHR.responseText);
        }
    });
}

function addBooking(sessionId, customerId) {
    var url = BOOKING_URL + "/add?sessionId=" + sessionId + "&customerId=" + customerId;

    $.ajax({
        type: "POST",
        url: url,
        success: function (response) {
            document.open();
            document.write(response);
            document.close();
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
            alert('ERROR: ' + jqXHR.responseText);
        }
    });
}