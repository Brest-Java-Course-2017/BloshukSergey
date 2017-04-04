var PREFIX_URL = "http://localhost:8088";
var SESSION_URL = "/session";
var CUSTOMER_URL = "/customer";
var BOOKING_URL = "/booking";
var customerId = getUrlParameter('id');

init();

function init() {
    if(customerId === undefined)
        return;

    var url = PREFIX_URL + CUSTOMER_URL + "/getById?id=" + customerId;

    $.ajax({
        type: "GET",
        url: url,
        dataType: "json",
        success: function (data) {
            $("#customerId").val(data.customerId);
            $("#name").val(data.name);
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
    if(customerId === undefined) {
        addCustomer();
        window.location = "index.html";
    }
    else {
        updateCustoer();
        window.location = "customerList.html";
    }

});


$("#btnCancel").click(function () {
    window.location = "customerList.html";
});

function addCustomer() {
    $.ajax({
        type: "POST",
        contentType: 'application/json',
        url: PREFIX_URL + CUSTOMER_URL + "/add",
        dataType: "json",
        data: ToJSON(),
        success: function (data, textStatus, jqXHR) {
            alert('Customer created successfully');
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('Error: ' + jqXHR.responseText);
        }
    });
}


function updateCustoer() {
    $.ajax({
        type: "PUT",
        contentType: 'application/json',
        url: PREFIX_URL + CUSTOMER_URL + "/update",
        dataType: "json",
        data: ToJSON(),
        success: function (data, textStatus, jqXHR) {
            alert('Customer updated successfully');
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('Error: ' + jqXHR.responseText);
        }
    });
}

function ToJSON() {
    return JSON.stringify({
        "customerId": customerId,
        "name": $("#name").val()
    });
}