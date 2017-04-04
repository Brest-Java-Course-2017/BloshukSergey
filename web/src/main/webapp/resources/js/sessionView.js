var SESSION_URL = "/session";

$("#btnOk").click(function () {
    var type = $("#sessionId").val() === "" ? "POST": "PUT";
    var url = SESSION_URL + ($("#sessionId").val() === "" ? "/add": "/update");

    $.ajax({
        type: type,
        url:  url,
        contentType: 'application/json',
        data: toJson(),
        success: function (response) {
            document.open();
            document.write(response);
            document.close();
        },
    })
});

$("#btnCancel").click(function () {
    window.location = "/session";
});

function toJson() {
    return JSON.stringify({
        "sessionId": $("#sessionId").val(),
        "movieName": $("#movieName").val(),
        "sessionDate": $("#sessionDate").val(),
        "totalSeats": $("#totalSeats").val(),
    });
}