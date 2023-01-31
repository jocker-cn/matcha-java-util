package org.jokerCN.match.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;

public class JacksonParse extends JsonBaseParseImpl {

    private final ObjectMapper objectMapper;

    public JacksonParse(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public JacksonParse(JsonParseResolve<ObjectMapper> objectMapper) {
        this.objectMapper = objectMapper.resolve();
    }

    @Override
    public <T> T fromJson(String json, Type typeOfT) {
        try {
            return objectMapper.readValue(json, typeToReference(typeOfT));
        } catch (JsonProcessingException e) {
            throw new JsonParseJumpException(e);
        }
    }

    @Override
    public <T> T fromJson(String json, Class<T> classOfT) {
        try {
            return objectMapper.readValue(json, classOfT);
        } catch (JsonProcessingException e) {
            throw new JsonParseJumpException(e);
        }
    }

    @Override
    public <T> T fromJson(Reader json, Class<T> classOfT) {
        try {
            return objectMapper.readValue(json,classOfT);
        } catch (IOException e) {
            throw new JsonParseJumpException(e);
        }
    }

    @Override
    public <T> T fromJson(Reader json, Type typeOfT) {
        try {
            return objectMapper.readValue(json,typeToReference(typeOfT));
        } catch (IOException e) {
            throw new JsonParseJumpException(e);
        }
    }

    @Override
    public String toJson(Object src, Type typeOfSrc) {
        return toJson(src);
    }

    @Override
    public String toJson(Object src) {
        try {
            return objectMapper.writeValueAsString(src);
        } catch (JsonProcessingException e) {
            throw new JsonParseJumpException(e);
        }
    }
}
