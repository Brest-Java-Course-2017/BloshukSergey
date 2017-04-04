var PREFIX_URL = "http://localhost:8088";
var SESSION_URL = "/session";
var CUSTOMER_URL = "/customer";
var BOOKING_URL = "/booking";
var sessionId = getUrlParameter('id');

init();

function init() {
    if(sessionId === undefined) {
        return;
    }

    var url = PREFIX_URL + BOOKING_URL + "/getCustomersBySessionId?id=" + sessionId;

    $.ajax({
        type: "GET",
        url: url,
        dataType: "json",
        success: renderList,
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
            alert('ERROR: ' + jqXHR.responseText);
        }
    });
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

function renderList(data) {
    data = data == null ? [] : (data instanceof Array ? data : [data]);

    $("#customerList tr").remove();

    $.each(data, drawCustomerRow);
}

function drawCustomerRow(index, customer) {
    var row = $("<tr />");
    $("#customerList").append(row);

    row.append($("<td>" + (index + 1) + "</td>"));
    row.append($("<td>" + customer.name + "</td>"));
    row.append($(
        "<td>" +
        '<div class="btn-group">' +
        '<input type="button" class="btn btn-danger" value="Delete" onclick="deleteFromSession(' + sessionId + ',' + customer.customerId+ ')" />' +
        '</div>' +
        '</td>'));
}

function deleteFromSession(sessionId, customerId) {
    var url = PREFIX_URL + BOOKING_URL + "/delete?sessionId=" + sessionId + "&customerId=" + customerId;

    $.ajax({
        type: "DELETE",
        url: url,
        dataType: "json",
        success: function (data, textStatus, jqXHR) {
            alert('Customer deleted from session successfully');
            init();
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
            alert('ERROR: ' + jqXHR.responseText);
        }
    });
}

$("#btnSearch").click(function () {
    var url = PREFIX_URL + CUSTOMER_URL + "/getByName?name=" + $("#name").val() + "%25";

    $.ajax({
        type: "GET",
        url: url,
        dataType: "json",
        success: renderCustomers,
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
            alert('ERROR: ' + jqXHR.responseText);
        }
    });
});

function renderCustomers(data) {
    data = data == null ? [] : (data instanceof Array ? data : [data]);

    $("#customerFindList tr").remove();

    $.each(data, drawFindCustomerRow);
}

function drawFindCustomerRow(index, customer) {
    var row = $("<tr />");
    $("#customerFindList").append(row);

    row.append($("<td>" + (index + 1) + "</td>"));
    row.append($("<td>" + customer.name + "</td>"));
    row.append($(
        "<td>" +
        '<div class="btn-group">' +
        '<input type="button" class="btn btn-danger" value="Add" onclick="addCustomerIntoSession(' + sessionId + ',' + customer.customerId+ ')" />' +
        '</div>' +
        '</td>'));
}

function addCustomerIntoSession(sessionId, customerId) {
    var url = PREFIX_URL + BOOKING_URL + "/add?sessionId=" + sessionId + "&customerId=" + customerId;

    $.ajax({
        type: "POST",
        url: url,
        dataType: "json",
        success: function (data, textStatus, jqXHR) {
            alert('Customer add to session successfully');
            init();
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
            alert('ERROR: ' + jqXHR.responseText);
        }
    });
}