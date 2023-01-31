package org.jokerCN.match.json;

import com.google.gson.Gson;

import java.io.Reader;
import java.lang.reflect.Type;

public class GsonParse extends JsonBaseParseImpl {

    private final Gson gson;

    public GsonParse(JsonParseResolve<Gson> jsonParseResolve) {
        this.gson = jsonParseResolve.resolve();
    }

    @Override
    public <T> T fromJson(String json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }

    @Override
    public <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    @Override
    public <T> T fromJson(Reader json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    @Override
    public <T> T fromJson(Reader json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }

    @Override
    public String toJson(Object src, Type typeOfSrc) {
        return gson.toJson(src, typeOfSrc);
    }

    @Override
    public String toJson(Object src) {
        return gson.toJson(src);
    }
}
