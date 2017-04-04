var CUSTOMER_URL = "/customer";

$("#btnOk").click(function () {
    var type = $("#customerId").val() === "" ? "POST": "PUT";
    var url = CUSTOMER_URL + ($("#customerId").val() === "" ? "/add": "/update");

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
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
            alert('ERROR: ' + jqXHR.responseText);
        }
    })
});

$("#btnCancel").click(function () {
    window.location = "/customer";
});

function toJson() {
    return JSON.stringify({
        "customerId": $("#customerId").val(),
        "name": $("#name").val(),
    });
}