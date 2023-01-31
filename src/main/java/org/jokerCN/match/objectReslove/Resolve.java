package org.jokerCN.match.objectReslove;

import org.jokerCN.match.Service;

public interface Resolve<T> extends Service {

    T resolve();

}
