function login() {
    const nick_name = $('#nick').val();
    if (nick_name === '') {
        alert('昵称不能为空！');
        return
    }
    $.ajax({
        url: '/login',
        data: JSON.stringify({
            'nick_name': nick_name
        }),
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        success: function (data) {
            if (!data['success']) {
                alert(data['message'])
            }
            else {
                sessionStorage.setItem("uname",nick_name);
                window.location.replace('/page/chatroom.html')
            }
        }
    })
}

$('#login').click(login);

