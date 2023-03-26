package org.jokerCN.matcha.objectReslove;

import org.jokerCN.matcha.Service;

public interface Resolve<T> extends Service {

    T resolve();

}
