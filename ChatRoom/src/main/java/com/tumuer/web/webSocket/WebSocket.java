package com.tumuer.web.webSocket;

import com.google.gson.Gson;
import com.tumuer.pojo.MessageForm;
import com.tumuer.service.UserService;
import com.tumuer.service.impl.UserServiceImpl;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author tumuer
 * @version v0.1.0
 * @date 2022/10/23
 * @project_name ChatRoom
 * @note
 */

@ServerEndpoint(value = "/chat", configurator = HttpSessionConfigurator.class)
public class WebSocket {

    // 用来存储每一个客户对象对应的ChatEndpoint对象
    private static Map<String, WebSocket> onlineUsers = new ConcurrentHashMap<>();
    // 声明session对象， 该对象可以给指定用户发送消息
    private Session session;
    //在登录时，使用HttpSession存储了用户名
    private HttpSession httpSession;

    private UserService userService = new UserServiceImpl();


    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        HttpSession httpSession = (HttpSession) endpointConfig.getUserProperties().get(HttpSession.class.getName());
        String user = (String) httpSession.getAttribute("user");
        if (user != null) {
            // 用户登录
            this.session = session;
            this.httpSession = httpSession;
            onlineUsers.put(user, this);
            System.out.println("建立连接成功！！！");
        } else {
            // 用户未登录，关闭WebSocket链接
            try {
                session.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @OnClose
    public void onClose() {
        String user = (String) httpSession.getAttribute("user");
        // 从online_users中删掉指定的用户
        onlineUsers.remove(user);
        // 当某个用户下线时，从Redis的set中删掉用户名，防止下次登录时用户名用不了
        userService.logout(user);
        System.out.println(user + "已下线");
    }

    @OnMessage
    public void onMessage(String message) throws IOException {
        //System.out.println("我收到消息了");
        // 先校验一下数据格式，如果数据格式没有错误，再发送给各个客户端。
        try {
            Gson gson = new Gson();
            MessageForm messageForm = gson.fromJson(message, MessageForm.class);
            String nickName = messageForm.getNickName();
            String msg = messageForm.getMsg();
            String postTime = messageForm.getPostTime();
            if (nickName != null && msg != null && postTime != null) {
                if (nickName.length() > 0 && msg.length() > 0 && postTime.length() > 1) {
                    //System.out.println(messageForm.toString());
                    broadcast(message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @OnError
    public void onError(Throwable throwable) throws Throwable {
        System.out.println("出错了！！！");
    }

    /**
     * 当某个用户发生消息时，OnMessage方法会被调用这个方法，将最新消息推送给其他用户
     *
     * @param message 用户发送的消息
     */
    private void broadcast(String message) {
        try {
            Set<String> names = onlineUsers.keySet();
            for (String name : names) {
                WebSocket webSocket = onlineUsers.get(name);
                webSocket.session.getBasicRemote().sendText(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
