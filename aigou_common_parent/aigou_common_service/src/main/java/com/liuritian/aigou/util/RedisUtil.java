package com.liuritian.aigou.util;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 获取连接池
 * 获取实例
 * <p>
 * 调用api
 */
public class RedisUtil {

    static JedisPool jedisPool = null;

    static {
        //连接池的设置：
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(20);//高潮期是最大连接数
        poolConfig.setMaxIdle(5);//空闲时最大连接数
        poolConfig.setMaxWaitMillis(5 * 1000);//最大等待时间
//        poolConfig.setTestOnBorrow(true);//接入时 测试是否通过
        String host = "127.0.0.1";
        int port = 6379;//端口
        int timeout = 5000;//超时
        String password = "123456";//redis密码
        jedisPool = new JedisPool(poolConfig, host, port, timeout, password);
    }

    public static void set(String key, String value) {
        //获取一个连接
        Jedis jedis = jedisPool.getResource();
        //调用api
        try {
            jedis.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }


    }

    public static String get(String key) {
        //获取一个连接
        Jedis jedis = jedisPool.getResource();
        //调用api
        try {
            return jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }


    }
}
