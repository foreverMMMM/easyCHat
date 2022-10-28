package com.tumuer.service.impl;

import com.tumuer.dao.UserDao;
import com.tumuer.dao.impl.UserDaoImpl;
import com.tumuer.service.UserService;

/**
 * @author tumuer
 * @version v0.1.0
 * @date 2022/10/24
 * @project_name ChatRoom
 * @note
 */
public class UserServiceImpl implements UserService {

    // 使用Dao层与数据库进行交互
    private UserDao userDao = new UserDaoImpl();

    /**
     * 将用户昵称放入set中
     *
     * @param nickName 用户昵称
     * 如果用户名不存在，则返回1，否则返回0
     */
    @Override
    public int login(String nickName) {
        if (!userDao.nickNameIsDuplicated(nickName)) {
            userDao.push2Set(nickName);
            return 1;
        }else {
            return 0;
        }
    }


    @Override
    public void logout(String nickName) {
        userDao.delFromSet(nickName);
    }
}
