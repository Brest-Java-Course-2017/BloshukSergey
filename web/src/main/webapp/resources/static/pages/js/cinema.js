function sessionOkClick(method, id) {
    var type = (method == "add" ? "POST": "PUT");
    $.ajax(
        {
            type: type,
            url: '/session/' + method,
            data: {
                'sessionId': id,
                'movieName': $("#movieName").val(),
                'sessionDate': $("#sessionDate").val(),
            },
            success: function (response) {
                console.log(response);
                document.open();
                document.write(response);
                document.close();
            },
            error: function (response) {
                console.error(response);
            }
        }
    );
}

function showSession(id) {
    $.ajax(
        {
            url: '/session/showSession?id=' + id,
            dataType: 'html',
            success: function (response) {
                console.log(response);
                document.open();
                document.write(response);
                document.close();
            },
            error: function (response) {
                console.error(response);
            }
        }
    );
}

function createUser() {
    $.ajax(
        {
            type: "POST",
            url: '/users/add',
            data: {
                'login': $("#inputLogin").val(),
                'password': $("#inputPassword").val(),
                'description': $("#inputDescription").val(),
            },
            success: function (response) {
                console.log(response);
                document.open();
                document.write(response);
                document.close();
            },
            error: function (response) {
                console.error(response);
            }
        }
    );
}

function updateSession(id) {
    $.ajax({
        url: '/session/update',
        data: {
            'sessionId': id,
            'movieName': $("#movieName").val(),
            'sessionDate': $("#sessionDate").val(),
        },
        success: function (response) {
            console.log(response);
            document.open();
            document.write(response);
            document.close();
        },
        error: function (response) {
            console.error(response);
        }
    });
}

function showDateToDate() {
    $.ajax(
        {
            url: '/session/getAllWithTicketsDateToDate?firstDate=' + $("#firstDate").val() + '&secondDate=' + $("#secondDate").val(),
            dataType: 'html',
            success: function (response) {
                console.log(response);
                document.open();
                document.write(response);
                document.close();
            },
            error: function (response) {
                console.error(response);
            }
        }
    );
}