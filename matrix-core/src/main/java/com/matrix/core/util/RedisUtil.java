package com.matrix.core.util;

import cn.hutool.core.util.StrUtil;
import com.matrix.core.cache.JsonRedisTemplate;
import com.matrix.core.config.RedisConfig;
import com.matrix.core.config.SpringBeansUtil;
import com.matrix.core.model.rest.MyGsonHttpMessageConverter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class RedisUtil {
    private static RedisTemplate redisTemplate = null;

    private static final RedisTemplate getRedisTemplate(){
        if(redisTemplate == null){
            if(SpringBeansUtil.getApplicationContext().containsBean(RedisConfig.DEFAULT_JSON_REDIS_TEMPLATE)){
                redisTemplate = SpringBeansUtil.getBean(RedisConfig.DEFAULT_JSON_REDIS_TEMPLATE, JsonRedisTemplate.class);
            }else{
                redisTemplate = SpringBeansUtil.getBean(JsonRedisTemplate.class);
            }
        }
        return redisTemplate;
    }

    public static <T> T getObject(String key, Type type) {
        byte[] bytes = (byte[]) getRedisTemplate().opsForValue().get(key);
        if(bytes==null)return null;
        if(type == String.class)
            return (T) new String(bytes, StandardCharsets.UTF_8);
        else{
            return MyGsonHttpMessageConverter.myGson().fromJson(new String(bytes,StandardCharsets.UTF_8),type);
        }
    }

    public static <T> void setObject(String key, T value, long time, TimeUnit timeUnit) {
        getRedisTemplate().opsForValue().set(key, value, time, timeUnit);
    }

    /**
     * Time default Seconds
     * **/
    public static <T> void setObject(String key, T value, long time) {
        setObject(key, value, time, TimeUnit.SECONDS);
    }

    public static <T> void setObject(String key, T value) {
        setObject(key, value, 0, TimeUnit.SECONDS);
    }

    /**
     * Time default Seconds
     * **/
    public static void setExpire(String key, long time) {
        setExpire(key, time, TimeUnit.SECONDS);
    }

    public static void setExpire(String key, long time, TimeUnit timeUnit) {
        getRedisTemplate().expire(key, time, timeUnit);
    }

    /**
     * Time default Seconds
     * **/
    public static long expire(String key) {
        return expire(key, TimeUnit.SECONDS);
    }

    public static long expire(String key, TimeUnit timeUnit) {
        return getRedisTemplate().getExpire(key, timeUnit);
    }

    public static boolean hasKey(String key) {
        return getRedisTemplate().hasKey(key);
    }

    public static void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                getRedisTemplate().delete(key[0]);
            } else {
                getRedisTemplate().delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    public static Collection<String> keys(String pattern) {
        return getRedisTemplate().keys(pattern);
    }

    public static void delByPrefix(String prefix){
       Set<String> keys = getRedisTemplate().keys(StrUtil.format("{}*",prefix));
       if(keys!=null && keys.size()>0)
          del(keys.toArray(new String[keys.size()]));
    }
}
