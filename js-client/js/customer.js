var host = "http://localhost:8088";

$.dto = null;

getAllCustomers();

function getAllCustomers() {
    $.ajax({
        type: 'GET',
        url: host + "/customer/getAllBySessionId?id=" + getUrlParameter('id'),
        dataType: "json", // data type of response
        success: renderList,
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
            alert('ERROR: ' + jqXHR.responseText);
        }
    });

    $("#sessionId").val(getUrlParameter("id"));
}

function renderList(data) {
    dto = data == null ? [] : (data instanceof Array ? data : [data]);
    $('#customerList tr').remove();

    $.each(dto, function (index, session) {
        drawRow(session);
    });
}

function drawRow(customer) {
    var row = $("<tr />");
    $("#customerList").append(row);

    row.append($("<td>" + '<a href="#" data-id="' + customer.customerId + '">' + customer.firstName + '</a></td>'));
    row.append($("<td>" + customer.lastName + "</td>"));
    row.append($("<td>" + customer.quantityTickets + "</td>"));
    row.append($("<td>" + '<a href="#" data-id="' + customer.sessionId + '">delete</a></td>'));
}

$(document).on("click", "a", function() {
    var action = $(this).text();
    var selectedCustomerId = $(this).data("id");
    if (action != "delete") {
        $.each(dto, function (index) {
            if (dto[index].customerId == selectedCustomerId) {
                $("#customerId").val(selectedCustomerId);
                $("#sessionId").val(dto[index].sessionId);
                $("#firstName").val(dto[index].firstName);
                $("#lastName").val(dto[index].lastName);
                $("#quantityTickets").val(dto[index].quantityTickets);
            }
        });
    } else {
        deleteCustomer(selectedCustomerId);
    }
});

function deleteCustomer(customerId) {
    $.ajax({
        type: 'DELETE',
        contentType: 'application/json',
        url: host + '/customer/delete?id=' + customerId,
        success: function (data, textStatus, jqXHR) {
            alert('Customer delete successfully');
            getAllCustomers();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('Error: ' + jqXHR.responseText);
        }
    });
}

$('#btnCustomerClean').click(function () {
    $("#customerId").val("");
    // $("#sessionId").val("");
    $("#firstName").val("");
    $("#lastName").val("");
    $("#quantityTickets").val("");
});

$('#btnCustomerSave').click(function () {
    if ($('#customerId').val() == '')
        addCustomer();
    else
        updateCustomer();
    return false;
});

function updateCustomer() {
    $.ajax({
        type: 'PUT',
        contentType: 'application/json',
        url: host + "/customer/update",
        data: formToJSON(),
        success: function (data, textStatus, jqXHR) {
            alert('Customer updated successfully');
            getAllCustomers();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('Error: ' + jqXHR.responseText);
        }
    });
}

function formToJSON() {
    var customerId = $('#customerId').val();
    return JSON.stringify({
        "customerId": customerId == "" ? null : customerId,
        "sessionId": $('#sessionId').val(),
        "firstName": $('#firstName').val(),
        "lastName": $('#lastName').val(),
        "quantityTickets" : $('#quantityTickets').val()
    });
}

function addCustomer() {
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: host + "/customer/add",
        dataType: "json",
        data: formToJSON(),
        success: function (data, textStatus, jqXHR) {
            alert('Customer created successfully');
            getAllCustomers();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('Error: ' + jqXHR.responseText);
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

