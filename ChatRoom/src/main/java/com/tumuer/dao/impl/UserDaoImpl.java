package com.tumuer.dao.impl;

import com.tumuer.dao.UserDao;
import com.tumuer.util.RedisUtils;
import redis.clients.jedis.Jedis;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author tumuer
 * @version v0.1.0
 * @date 2022/10/24
 * @project_name ChatRoom
 * @note
 */
public class UserDaoImpl implements UserDao {

    private static String userSet;

    static {
        try {
            Properties properties = new Properties();
            InputStream resourceAsStream = UserDaoImpl.class.getClassLoader().getResourceAsStream("redisConfig.properties");
            properties.load(resourceAsStream);
            userSet = properties.getProperty("online_user_set");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void push2Set(String nickName) {
        Jedis jedis = null;
        try {
            jedis = RedisUtils.getJedisResource();
            jedis.sadd(userSet,nickName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            RedisUtils.closeJedisResource(jedis);
        }
    }

    @Override
    public boolean nickNameIsDuplicated(String nickName) {
        Jedis jedis = RedisUtils.getJedisResource();
        return jedis.sismember(userSet, nickName);
    }

    /**
     * 从redis中删除用户名
     *
     * @param nickName 用户名
     */
    @Override
    public void delFromSet(String nickName) {
        Jedis jedis = null;
        try {
            jedis = RedisUtils.getJedisResource();
            if (nickNameIsDuplicated(nickName)) {
                jedis.srem(userSet,nickName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            RedisUtils.closeJedisResource(jedis);
        }
    }
}
