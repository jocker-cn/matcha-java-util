package org.jokerCN.matcha.json;


import java.io.Reader;
import java.lang.reflect.Type;

public interface JsonParse {

    <T> T fromJson(String json, Type typeOfT);

    <T> T fromJson(String json, Class<T> classOfT);

    <T> T fromJson(Reader json, Class<T> classOfT);

    <T> T fromJson(Reader json, Type typeOfT);

    String toJson(Object src, Type typeOfSrc);

    String toJson(Object src);

}
