package com.matrix.core.model.rest;

import com.google.gson.*;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.lang.Nullable;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.Date;

public class MyGsonHttpMessageConverter extends GsonHttpMessageConverter {

    public static Gson myGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });
        builder.registerTypeAdapter(Timestamp.class, new JsonDeserializer<Timestamp>() {
            public Timestamp deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Timestamp(json.getAsJsonPrimitive().getAsLong());
            }
        });
        builder.registerTypeAdapter(Date.class, new JsonSerializer<Date>() {
            public JsonElement serialize(Date arg0, Type arg1, JsonSerializationContext arg2) {
                return arg0 != null ? new JsonPrimitive(arg0.getTime()) : null;
            }
        });
        builder.registerTypeAdapter(Timestamp.class, new JsonSerializer<Timestamp>() {
            public JsonElement serialize(Timestamp arg0, Type arg1, JsonSerializationContext arg2) {
                return arg0 != null ? new JsonPrimitive(arg0.getTime()) : null;
            }
        });
        return builder.disableHtmlEscaping().create();
    }

    public MyGsonHttpMessageConverter() {
        super();
        setGson(myGson());
    }

    public boolean canWrite(Class<?> clazz, @Nullable MediaType mediaType) {
        return clazz == Resp.class;
    }
}