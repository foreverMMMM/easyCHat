function generate_chat_list_html(chat_list) {
    let msg_list = '';
    let msg;
    let nick;
    let post_time;
    for (let i = 0; i < chat_list.length; i++) {
        msg = chat_list[i]['msg'];
        nick = chat_list[i]['nick_name'];
        post_time = chat_list[i]['post_time'];
        let line = '<div class="columns" style="margin-top: 5px">';
        line += '<div class="user_name">';
        if (nick === sessionStorage.getItem("uname")) {
            line += '<span class="label label-success centered">' + '我' + '</span>';
        } else {
            line += '<span class="label label-secondary">' + nick + '</span>';
        }
        line += '</div>';
        line += '<div class="text-gray">';
        line += '<span class="label">' + post_time + '</span>';
        line += '</div>';
        line += '<br />';
        line += '<div class="float-right">' + msg + '</div>';
        line += '</div>';
        msg_list += line;
    }
    return msg_list
}

var msgList = new Array();

function refreshChat(msgData) {
    const tmp = JSON.parse(msgData)
    tmp["post_time"] = moment(parseInt(tmp["post_time"])).format('h:mm:ss');
    msgList.unshift(tmp);
    if (msgList.length > 30) {
        msgList.splice(30,msgList.length-30);
    }
    const chat_list_html = generate_chat_list_html(msgList);

    $(".panel-body").html(chat_list_html)

}


const wsUri = "ws://" + document.location.host + "/chat";
const ws = new WebSocket(wsUri);


// 注册事件
ws.addEventListener("open", handleOpen, false);
ws.addEventListener("close", handleClose, false);
ws.addEventListener("error", handleError, false);
ws.addEventListener("message", handleMessage, false);


function handleOpen() {
    console.log("Connected to " + wsUri);
}

function handleError() {
    console.log("WebSocket Error");

}

function handleClose() {
    sessionStorage.removeItem("uname");
    console.log("WebSocket Close");
}


function handleMessage(e) {
    const respData = e.data;
    console.log("received :" + respData);
    refreshChat(respData);
}

function handleSendBtnClick() {
    const msg = $('.form-input').val().trim();
    const uname = sessionStorage.getItem("uname");
    if (msg === '') {
        alert('内容不能为空！');
        return
    }
    if (!uname) {
        alert("登录过期，请重新登录");
        window.location.replace("/");
    }
    console.log("start to sending message");

    const messageData = JSON.stringify({
        'nick_name': uname,
        'msg': msg,
        "post_time": new Date().getTime()
    });
    ws.send(messageData);
    $('.form-input').val(""); // 将输入框清空
}

$('#post').click(handleSendBtnClick);