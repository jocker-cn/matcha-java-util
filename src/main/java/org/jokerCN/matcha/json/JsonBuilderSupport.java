package org.jokerCN.matcha.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class JsonBuilderSupport {


    public static Gson getGson() {
        return new Gson();
    }

    public static ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }
}
