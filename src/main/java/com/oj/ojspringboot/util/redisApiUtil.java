package com.oj.ojspringboot.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 操作redisApi的工具类
 *  @Component注解在一个类上，表示将此类标记为Spring容器中的一个Bean。
 *  自动注入配置
 * @author ld
 * @since 1.8
 * @version 17
 */

@Slf4j
@Component
public class redisApiUtil {

    @Autowired
    private static RedisTemplate<String, String> redisTemplate;

    /**
     * 实例化
     */
    public redisApiUtil(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    /**
     *set key value
     */
    public static boolean redisSetKey(String key,String value,int second){
        try {
            log.info(key);
            redisTemplate.boundValueOps(key).set(value, second, TimeUnit.SECONDS);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }
    public static boolean redisSetKey(String key,String value){
        try {
            redisTemplate.boundValueOps(key).set(value);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public static String redisGetKey(String key){
        try{
            return redisTemplate.opsForValue().get(key);
        }catch (Exception ex){
            ex.printStackTrace();
            return "value==null||key not exist";
        }
    }

}
