package org.jokerCN.matcha.util;

import org.junit.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author jokerCN <a href="https://github.com/jocker-cn">
 */
public class ObjectPoolTest {


    Logger logger = Logger.getLogger(ObjectPoolTest.class.getName());

    private static final ObjectPool.ObjectCreator<ObjectTest> createObjectPool = ObjectTest::new;


    @Test
    public void objectBaseTest() {
        ObjectPool<ObjectTest> objectTestObjectPool = ObjectPool.newObjectPool(createObjectPool);

        ObjectTest objectTest = objectTestObjectPool.get();
        objectTest.clear();
        ObjectTest objectTest2 = objectTestObjectPool.get();
        logger.log(Level.INFO,"ok");

    }


    private record ObjectTest(ObjectRecord.ObjectHandle<ObjectTest> handle) {


        public void clear() {
                handle.recycle(this);
            }
        }
}
