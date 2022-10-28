package com.tumuer.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * @author tumuer
 * @version v0.1.0
 * @date 2022/10/5
 * @project_name ChatRoom
 * @note
 */
public class MessageForm {

    private final String msg; // 用户发送的信息

    @SerializedName("nick_name")
    private final String nickName; // 用户昵称

    @SerializedName("post_time")
    private final String postTime; // 发送消息的时间戳

    public String getMsg() {
        return this.msg;
    }

    public String getNickName() {
        return this.nickName;
    }

    public String getPostTime() {
        return this.postTime;
    }

    public MessageForm(String msg, String nickName, String postTime) {
        this.msg = msg;
        this.nickName = nickName;
        this.postTime = postTime;
    }

    @Override
    public String toString() {
        return "MessageForm{" +
                "msg='" + msg + '\'' +
                ", nickName='" + nickName + '\'' +
                ", postTime='" + postTime + '\'' +
                '}';
    }
}
