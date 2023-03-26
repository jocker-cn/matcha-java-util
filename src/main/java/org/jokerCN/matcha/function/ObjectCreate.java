package org.jokerCN.matcha.function;

import java.util.function.Supplier;

@FunctionalInterface
public interface ObjectCreate<T> {

    T create();
}
