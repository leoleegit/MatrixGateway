package com.matrix.core.model.rest.resp;

import com.google.gson.JsonObject;

public class JsonBuilder{
    private JsonObject jsonObject;

    public JsonBuilder(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public JsonBuilder add(String property, String value) { jsonObject.addProperty(property,value); return this;}

    public JsonBuilder add(String property, Number value) {
        jsonObject.addProperty(property,value);return this;
    }

    public JsonBuilder add(String property, Boolean value) {
        jsonObject.addProperty(property,value);return this;
    }

    public JsonBuilder add(String property, Character value) {
        jsonObject.addProperty(property,value);return this;
    }

    public static JsonBuilder builder(){
        JsonBuilder jsonBuilder = new JsonBuilder(new JsonObject());
        return jsonBuilder;
    }

    public JsonObject build(){
        return jsonObject;
    }
}
