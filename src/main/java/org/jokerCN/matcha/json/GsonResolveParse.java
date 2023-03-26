package org.jokerCN.matcha.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonResolveParse implements JsonParseResolve<Gson> {

    private final GsonBuilder builder;

    private final Gson gson;

    public GsonResolveParse(GsonBuilder builder) {
        this.builder = builder;
        this.gson = this.builder.create();
    }

    public GsonResolveParse(Gson gson) {
        this.gson = gson;
        this.builder = gson.newBuilder();
    }

    @Override
    public Gson resolve() {
        return gson;
    }

}
