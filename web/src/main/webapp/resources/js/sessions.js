var SESSION_URL = "/session";

function deleteSession(id) {
    var url = SESSION_URL + "/delete?id=" + id;

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

function gotoEdit(id) {
    var url = SESSION_URL + "/sessionView?id=" + id;

    $.ajax({
        type: "GET",
        url: url,
        success: function (response) {
            document.open();
            document.write(response);
            document.close();
        }
    });
}

$("#btnAdd").click(function () {
    $.ajax({
        type: "GET",
        url: SESSION_URL + "/sessionView",
        success: function (response) {
            document.open();
            document.write(response);
            document.close();
        }
    });
});
