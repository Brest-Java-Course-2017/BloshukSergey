function editUser(id) {
    $.ajax(
        {
            url: '/users/user?id=' + id,
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

function createUserPage() {
    $.ajax(
        {
            url: '/users/add',
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

function deleteUser(id) {
    $.ajax(
        {
            type: 'DELETE',
            url: '/users/del?userId=' + id
        }
    );
}

function updateUser(id) {
    $.ajax({
        type: 'PUT',
        url: '/users/upd',
        data: {
            'id': id,
            'login': $('#inputLogin').val(),
            'password': $('#inputPassword').val(),
            'description': $('#inputDescription').val()
        }
    });
}