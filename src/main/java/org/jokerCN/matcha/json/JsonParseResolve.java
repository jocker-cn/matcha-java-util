package org.jokerCN.matcha.json;


import org.jokerCN.matcha.objectReslove.Resolve;

public interface JsonParseResolve<T> extends Resolve<T> {

    @Override
    T resolve();
}
