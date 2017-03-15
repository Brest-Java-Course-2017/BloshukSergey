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

