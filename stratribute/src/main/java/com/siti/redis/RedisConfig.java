package com.siti.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

/**
 * @author zhangt
 */

@Repository
public class RedisConfig {
 
    @SuppressWarnings("rawtypes")
	@Autowired
    private RedisTemplate redisTemplate;
	
    /**
     * 序列化 , 否则可能会出现乱码
     * @param redisTemplate
     */
   
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Autowired
    public void setRedis(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        //设置序列化Key的实例化对象
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //设置序列化Value的实例化对象
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        
        redisTemplate.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());
        
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        
    }
    
}

