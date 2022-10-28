package com.tumuer.service;

/**
 * @author tumuer
 * @version v0.1.0
 * @date 2022/10/5
 * @project_name ChatRoom
 * @note
 */
public interface UserService {

    /**
     * 将用户昵称放入set中
     * @param nickName 用户昵称
     * 返回1或0
     *  1  登录成功
     *  0  用户名重复
     */
    public int login(String nickName);

    public void logout(String nickName);
}
