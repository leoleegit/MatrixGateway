package com.matrix.core.config;

import com.matrix.core.cache.JsonRedisTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Slf4j
@Configuration
@ConfigurationProperties
@EnableCaching
@RefreshScope
public class RedisConfig extends CachingConfigurerSupport {
    public final static String DEFAULT_STRING_REDIS_TEMPLATE = "DEFAULT_STRING_REDIS_TEMPLATE";
    public final static String DEFAULT_JSON_REDIS_TEMPLATE   = "DEFAULT_JSON_REDIS_TEMPLATE";

    @Value("${matrix.redis.database}")
    private int defaultDatabase;

    @Value("${matrix.redis.host}")
    private String host;

    @Value("${matrix.redis.port}")
    private int port;

    @Value("${matrix.redis.timeout}")
    private int timeout;

    @Value("${matrix.redis.password}")
    private String password;

    @RefreshScope
    @Bean(name = "defaultRedisConnectionFactory")
    public RedisConnectionFactory redisConnectionFactory(){
        LettuceConnectionFactory jedisConnectionFactory = new LettuceConnectionFactory();
        jedisConnectionFactory.setHostName(host);
        jedisConnectionFactory.setPort(port);
        if (defaultDatabase != 0) {
            jedisConnectionFactory.setDatabase(defaultDatabase);
        }
        jedisConnectionFactory.setTimeout(timeout);
        jedisConnectionFactory.afterPropertiesSet();
        return jedisConnectionFactory;
    }

    @Primary
    @Bean(name = DEFAULT_STRING_REDIS_TEMPLATE)
    public StringRedisTemplate stringRedisTemplate(@Qualifier("defaultRedisConnectionFactory") RedisConnectionFactory jedisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(jedisConnectionFactory);
        return template;
    }

    @Primary
    @Bean(name = DEFAULT_JSON_REDIS_TEMPLATE)
    public JsonRedisTemplate jsonRedisTemplate(@Qualifier("defaultRedisConnectionFactory") RedisConnectionFactory jedisConnectionFactory) {
        JsonRedisTemplate template = new JsonRedisTemplate();
        template.setConnectionFactory(jedisConnectionFactory);
        return template;
    }
}
