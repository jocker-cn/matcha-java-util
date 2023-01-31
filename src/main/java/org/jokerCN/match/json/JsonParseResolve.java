package org.jokerCN.match.json;


import org.jokerCN.match.objectReslove.Resolve;

public interface JsonParseResolve<T> extends Resolve<T> {

    @Override
    T resolve();
}
