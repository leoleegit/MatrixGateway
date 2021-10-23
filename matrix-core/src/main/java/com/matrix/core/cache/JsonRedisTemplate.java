package com.matrix.core.cache;

import cn.hutool.core.thread.ThreadUtil;
import com.google.gson.JsonObject;
import com.matrix.core.model.rest.MyGsonHttpMessageConverter;
import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.lang.Nullable;

import java.nio.charset.StandardCharsets;

public class JsonRedisTemplate extends RedisTemplate<String, JsonObject> {
    static final byte[] EMPTY_ARRAY = new byte[0];
    static boolean isEmpty(@Nullable byte[] data) {
        return data == null || data.length == 0;
    }

    public JsonRedisTemplate() {
        this.setKeySerializer(RedisSerializer.string());
        this.setValueSerializer(new JsonRedisSerializer());
        this.setHashKeySerializer(RedisSerializer.string());
        this.setHashValueSerializer(new JsonRedisSerializer());
    }

    public JsonRedisTemplate(RedisConnectionFactory connectionFactory) {
        this();
        this.setConnectionFactory(connectionFactory);
        this.afterPropertiesSet();
    }

    protected RedisConnection preProcessConnection(RedisConnection connection, boolean existingConnection) {
        return new DefaultStringRedisConnection(connection);
    }

    class JsonRedisSerializer implements RedisSerializer{


        @Override
        public byte[] serialize(Object o) throws SerializationException {
            if(o == null)
                return EMPTY_ARRAY;
            if(o instanceof String){
                return ((String) o).getBytes(StandardCharsets.UTF_8);
            }else{
                return MyGsonHttpMessageConverter.myGson().toJson(o).getBytes(StandardCharsets.UTF_8);
            }
        }

        @Override
        public Object deserialize(byte[] bytes) throws SerializationException {
            if (isEmpty(bytes)) {
                return null;
            } else {
                try {
//                    String string = new String(bytes);
//                    if((string.startsWith("{") && string.endsWith("}")) ||
//                            string.startsWith("[") && string.endsWith("]"))
//                        return MyGsonHttpMessageConverter.myGson().fromJson(new String(bytes,StandardCharsets.UTF_8),JsonObject.class);
                    return bytes;
                }
                catch (Exception var3) {
                    throw new SerializationException("Cannot deserialize", var3);
                }
            }
        }

        @Override
        public boolean canSerialize(Class type) {
            return true;
        }
    }
}
