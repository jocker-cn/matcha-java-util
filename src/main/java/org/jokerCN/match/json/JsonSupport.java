package org.jokerCN.match.json;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;

import java.util.Optional;

public class JsonSupport {

    public static final JsonParse DEFAULT_JSON_PARSE = new GsonParse(createDefaultGson(null));

    public static JsonParse getDefaultParse() {
        return DEFAULT_JSON_PARSE;
    }

    public static JsonParse getGsonParse(Gson gson) {
        return new GsonParse(() -> gson);
    }

    public static JsonParse getGsonParse(JsonParseResolve<Gson> jsonParseResolve) {
        return new GsonParse(jsonParseResolve);
    }

    public static JsonParse getGsonParse(GsonBuilder gsonBuilder) {
        return new GsonParse(new GsonResolveParse(gsonBuilder));
    }


    public static JsonParse getJacksonParse(Module... modules) {
        return new JacksonParse(createDefaultObjectMapper(modules));
    }


    public static JsonParse getJacksonParse(JacksonResolveParse jacksonResolveParse) {
        return new JacksonParse(jacksonResolveParse);
    }


    private static JsonParseResolve<ObjectMapper> createDefaultObjectMapper(Module... modules) {
        ObjectMapper objectMapper = new ObjectMapper();
        for (Module module : modules) {
            objectMapper.registerModule(module);
        }
        //null字段不参与序列化
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        //未知参数校验
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return new JacksonResolveParse(objectMapper);
    }

    private static JsonParseResolve<Gson> createDefaultGson(GsonBuilder gsonBuilder) {
        return () -> Optional.ofNullable(gsonBuilder).orElse(new GsonBuilder())
                .setLongSerializationPolicy(LongSerializationPolicy.STRING)
//                .serializeNulls()
                .setDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
                .create();
    }


}
