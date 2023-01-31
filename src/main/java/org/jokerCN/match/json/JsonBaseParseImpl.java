package org.jokerCN.match.json;

import com.fasterxml.jackson.core.type.TypeReference;

import java.lang.reflect.Type;

public abstract class JsonBaseParseImpl implements JsonParse {


    protected <T> TypeReference<T> typeToReference(Type type) {
        return new TypeReference<T>() {
            @Override
            public Type getType() {
                return type;
            }

            @Override
            public int compareTo(TypeReference<T> o) {
                return super.compareTo(o);
            }
        };
    }

    protected <T> Type referenceToType(TypeReference<T> tTypeReference) {
        return tTypeReference.getType();
    }

}
