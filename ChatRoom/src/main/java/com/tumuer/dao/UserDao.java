package com.tumuer.dao;

/**
 * @author tumuer
 * @version v0.1.0
 * @date 2022/10/24
 * @project_name ChatRoom
 * @note
 */
public interface UserDao {

    public void push2Set(String nickName);

    public boolean nickNameIsDuplicated(String nickName);


    /**
     * 从redis中删除用户名
     * @param nickName 用户名
     */
    public void delFromSet(String nickName);
}
