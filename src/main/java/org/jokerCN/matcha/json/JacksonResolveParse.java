package org.jokerCN.matcha.json;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonResolveParse implements JsonParseResolve<ObjectMapper>{

    private final ObjectMapper objectMapper;

    public JacksonResolveParse(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public ObjectMapper resolve() {
        return objectMapper;
    }
}
