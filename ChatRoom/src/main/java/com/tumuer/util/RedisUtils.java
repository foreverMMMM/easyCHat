package com.tumuer.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author tumuer
 * @version v0.1.0
 * @date 2022/10/5
 * @project_name ChatRoom
 * @note
 */


public class RedisUtils {

   private static JedisPool jedisPool;

   static {
       try {
           Properties properties = new Properties();
           // 读取redis配置文件
           InputStream resourceAsStream = RedisUtils.class.getClassLoader().getResourceAsStream("jedisConfig.properties");
           // 加载配置数据
           properties.load(resourceAsStream);

           // 获取相关配置
           String host = properties.getProperty("jedis.host");
           int port = Integer.parseInt(properties.getProperty("jedis.port"));
           int maxTotal = Integer.parseInt(properties.getProperty("jedis.maxTotal"));
           int maxIdle = Integer.parseInt(properties.getProperty("jedis.maxIdle"));

           // 配置Jedis连接池参数
           JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
           jedisPoolConfig.setMaxIdle(maxIdle);
           jedisPoolConfig.setMaxTotal(maxTotal);

           // 初始化jedis连接池
           jedisPool = new JedisPool(jedisPoolConfig, host, port);
       } catch (Exception e) {
           e.printStackTrace();
       }

   }

   // 从jedis连接池中获取链接
    public static Jedis getJedisResource() {
       return jedisPool.getResource();
    }

    // 关闭链接
    public static void closeJedisResource(Jedis jedis) {
       if (jedis != null) {
           jedis.close();
       }
    }
}
