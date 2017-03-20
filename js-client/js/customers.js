var host = "http://localhost:8088";

$.dto = null;

getAllCustomers();

function getAllCustomers() {
    $.ajax({
        type: 'GET',
        url: host + "/customer/getAll",
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
    row.append($("<td>" + '<a href="#" data-id="' + customer.customerId + '">edit</a> ' +
                          '<a href="#" data-id="' + customer.customerId + '">delete</a></td>'));
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
    $("#name").val("");
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

function formToJSON() {
    var customerId = $('#customerId').val();
    return JSON.stringify({
        "customerId": customerId == "" ? null : customerId,
        "name": $('#name').val(),
    });
}