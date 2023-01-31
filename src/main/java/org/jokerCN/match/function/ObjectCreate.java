package org.jokerCN.match.function;

import java.util.function.Supplier;

@FunctionalInterface
public interface ObjectCreate<T> {

    T create();
}
