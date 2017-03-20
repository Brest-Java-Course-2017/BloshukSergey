var host = "http://localhost:8088";

$.dto = null;
$.dto2 = null;

getCustomersBySessionId();

function getCustomersBySessionId() {
    $.ajax({
        type: 'GET',
        url: host + "/booking/getCustomersBySessionId?id=" + getUrlParameter('id'),
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
    $('#customerList tr').remove();

    $.each(dto, function (index, session) {
        drawRow(index, session);
    });
}

function drawRow(index, customer) {
    var row = $("<tr />");
    $("#customerList").append(row);

    row.append($("<td>" + (index + 1) + "</td>"));
    row.append($("<td>" + customer.name + "</td>"));
    row.append($("<td>" + '<a href="#" data-id="' + customer.customerId + '">delete</a></td>'));
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

$(document).on("click", "a", function() {
    var action = $(this).text();
    var selectedCustomerId = $(this).data("id");

    if (action == "delete") {
        deleteCustomer(selectedCustomerId);
    }
    else if (action == "edit") {
        $.each(dto, function (index) {
            if (dto[index].customerId == selectedCustomerId) {
                $("#customerId").val(selectedCustomerId);
                $("#name").val(dto[index].name);
            }
        });
    }
    else if (action == "add") {
        $.ajax({
            type: 'POST',
            contentType: 'application/json',
            url: host + "/booking/add?sessionId=" + getUrlParameter('id') + "&customerId=" + selectedCustomerId,
            dataType: "json",
            // data: ToJSON(getUrlParameter('id'), selectedCustomerId),
            success: function (data, textStatus, jqXHR) {
                alert('Customer created successfully');
                getAllCustomers();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert('Error: ' + jqXHR.responseText);
            }
        });
    }
});

function deleteCustomer(customerId) {
    $.ajax({
        type: 'DELETE',
        contentType: 'application/json',
        url: host + '/booking/delete?sessionId=' + getUrlParameter('id') + '&customerId=' + customerId,
        success: function (data, textStatus, jqXHR) {
            alert('Customer delete successfully');
            getCustomersBySessionId();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('Error: ' + jqXHR.responseText);
        }
    });
}

$('#btnSearch').click(function () {
    getCustomersByName($("#name").val());
});

function getCustomersByName(name) {
    $.ajax({
        type: 'GET',
        url: host + "/customer/getByName?name=%25" + name + "%25",
        dataType: "json",
        success: renderFoundList,
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
            alert('ERROR: ' + jqXHR.responseText);
        }
    });
}

function renderFoundList(data) {
    dto2 = data == null ? [] : (data instanceof Array ? data : [data]);
    $('#foundCustomerList tr').remove();

    $.each(dto2, function (index, session) {
        drawFoundRow(index, session);
    });
}

function drawFoundRow(index, customer) {
    var row = $("<tr />");
    $("#foundCustomerList").append(row);

    row.append($("<td>" + (index + 1) + "</td>"));
    row.append($("<td>" + customer.name + "</td>"));
    row.append($("<td>" + '<a href="#" data-id="' + customer.customerId + '">add</a></td>'));
}

function ToJSON(sessionId, customerId) {
    return JSON.stringify({
        "sessionId": parseInt(sessionId, 10),
        "customerId": customerId,
    });
}