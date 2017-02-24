function editUser(id) {
    $.ajax(
        {
            url: '/user?id=' + id,
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

function createUserPost() {
    $.ajax(
        {
            type: "POST",
            url: '/users/add',
            data: { 'login': $("#inputLogin").val(), 'password': $("#inputPassword").val() },
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
