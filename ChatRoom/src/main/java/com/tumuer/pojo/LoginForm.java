package com.tumuer.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * @author tumuer
 * @version v0.1.0
 * @date 2022/10/5
 * @project_name ChatRoom
 * @note
 */
public class LoginForm {

    @SerializedName("nick_name")
    private final String nickName;

    public LoginForm(String nickName) {
        this.nickName = nickName;
    }

    public String getNickName() {
        return this.nickName;
    }

}
