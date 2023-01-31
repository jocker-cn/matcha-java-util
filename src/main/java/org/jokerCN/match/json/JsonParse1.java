package org.jokerCN.match.json;

import java.io.Reader;
import java.lang.reflect.Type;

public class JsonParse1 {

    private static final JsonParse JSON_BASE_PARSE = new GsonParse(null);



    public static <T> T fromJson(String json, Type typeOfT) {
        return JSON_BASE_PARSE.fromJson(json, typeOfT);
    }


    public <T> T fromJson(String json, Class<T> classOfT) {
        return JSON_BASE_PARSE.fromJson(json, classOfT);
    }


    public <T> T fromJson(Reader json, Class<T> classOfT) {
        return JSON_BASE_PARSE.fromJson(json, classOfT);
    }


    public <T> T fromJson(Reader json, Type typeOfT) {
        return JSON_BASE_PARSE.fromJson(json, typeOfT);
    }


    public String toJson(Object src, Type typeOfSrc) {
        return JSON_BASE_PARSE.toJson(src, typeOfSrc);
    }


    public String toJson(Object src) {
        return JSON_BASE_PARSE.toJson(src);
    }
}
