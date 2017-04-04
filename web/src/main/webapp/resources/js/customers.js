var CUSTOMER_URL = "/customer";

function gotoEdit(id) {
    var url = CUSTOMER_URL + "/customerView?id=" + id;

    $.ajax({
        type: "GET",
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

function deleteCustomer(id) {
    var url = CUSTOMER_URL + "/delete?id=" + id;

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

$("#btnAdd").click(function () {
    $.ajax({
        type: "GET",
        url: CUSTOMER_URL + "/customerView",
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
});
