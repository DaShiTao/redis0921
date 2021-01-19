package com.atguigu;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;

public class RedisUtil {
    public static void main(String[] args) {
        //建立连接，new jedis ，需要传递主机名和端口号
        Jedis jedis = RedisUtil.getJedis();

        jedis.set("k1000", "v1000");
        Set<String> keyset = jedis.keys("*");
        for (String key : keyset) {
            System.out.println(key);
        }


        //关闭连接，释放资源
        jedis.close();
    }

    private static JedisPool jedisPool=null;
    
    public static Jedis getJedis(){
        
        if(jedisPool==null){

            JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
            jedisPoolConfig.setMaxTotal(100);
            jedisPoolConfig.setMinIdle(20);
            jedisPoolConfig.setMaxIdle(30);

            jedisPoolConfig.setBlockWhenExhausted(true);
            jedisPoolConfig.setMaxWaitMillis(5000);
            
            jedisPoolConfig.setTestOnBorrow(true);

            jedisPool = new JedisPool("hadoop102", 6379);
        }
        Jedis jedis = jedisPool.getResource();
        return jedis;
    }
}
