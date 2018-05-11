package com.ejavashop.core;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtil {

    private static JedisPool pool = null;  
    
    private static final Integer defaultTimeout = 3000;
    
    @Deprecated
    public static Jedis createJedis() {
        Jedis jedis = new Jedis("127.0.0.1");
        return jedis;
    }
    @Deprecated
    public static Jedis createJedis(String host, int port) {
        Jedis jedis = new Jedis(host, port);

        return jedis;
    }
    @Deprecated
    public static Jedis createJedis(String host, int port, String password) {
        Jedis jedis = new Jedis(host, port);
        
        if("nopass".equals(password) == false){
        	jedis.auth(password);
        }
        
        return jedis;
    }
    @Deprecated
    public static void DisconnectRedis(Jedis jedis){
    		jedis.disconnect();
    }
    @Deprecated
    public static void QuitRedis(Jedis jedis){
    		jedis.quit();
    }
    
    @Deprecated
    public static void CloseRedis(Jedis jedis){
    		jedis.close();
    }
    
    public static void returnJedis(Jedis jedis){
		jedis.close();
}
    
    public static JedisPool getPool(String host, int port, String password) {
       JedisPoolConfig config = new JedisPoolConfig();  
       config.setMaxTotal(100);
       config.setMaxWaitMillis(10000);
       config.setTestOnBorrow(true);
       if("nopass".equals(password) == false){
    	   pool = new JedisPool(config, host, port , defaultTimeout, password);
       }
       else{
    	   pool = new JedisPool(config, host, port);
       }
    	return pool;
    }
    
    public static Jedis getJedis (String host, int port, String password) {
    	if (pool == null){
    		getPool(host,port, password);
    	}
    	Jedis jedis = pool.getResource(); 

    	return jedis;
    }
    
}
