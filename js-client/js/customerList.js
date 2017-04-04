var PREFIX_URL = "http://localhost:8088";
var SESSION_URL = "/session";
var CUSTOMER_URL = "/customer";
var BOOKING_URL = "/booking";

getCustomerList();

function getCustomerList() {
    var url = PREFIX_URL + CUSTOMER_URL + "/getAll";

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
        '<input type="button" class="btn btn-default" value="Edit" onclick="gotoViewCustomer(' + customer.customerId+ ')" />' +
        '<input type="button" class="btn btn-danger" value="Delete" onclick="deleteCustomer(' + customer.customerId + ')" />' +
        '</div>' +
        '</td>'));
}

function gotoViewCustomer(id) {
    window.location = "viewCustomer.html?id=" + id;
}

function deleteCustomer(id) {
    var url = PREFIX_URL + CUSTOMER_URL + "/delete?id=" + id;

    $.ajax({
        type: "DELETE",
        url: url,
        success: function (data, textStatus, jqXHR) {
            alert('Customer deleted successfully');
            getCustomerList();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('delete error: ' + textStatus + id);
        }
    });
}

$("#btnAdd").click(function () {
    window.location = "viewCustomer.html";
});