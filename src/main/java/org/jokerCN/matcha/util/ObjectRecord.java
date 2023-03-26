/*
 * Copyright 2013 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package org.jokerCN.matcha.util;

import org.jctools.queues.MessagePassingQueue;
import org.jctools.queues.atomic.MpscChunkedAtomicArrayQueue;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * <p>
 * 此类来自netty(Recycler)，license请查看文件头
 * 实现逻辑与Recycler保持一直，接口方法稍有改动
 * 队列使用 {@link MpscChunkedAtomicArrayQueue}
 * </p>
 *
 * <p>
 * This class comes from netty (Recycler), please check the java file header for license
 * The implementation logic remains the same as that of Recycler, and the interface method is slightly changed
 * </p>
 *
 * @author jokerCN <a href="https://github.com/jocker-cn">
 */
public abstract class ObjectRecord<T> {

    /**
     * 默认对象池 最大限制
     */
    private static final int DEFAULT_INITIAL_MAX_CAPACITY = 4 * 1024;

    /**
     * 默认对象池 初始大小 最大值
     */
    private static final int DEFAULT_INITIAL_MAX = DEFAULT_INITIAL_MAX_CAPACITY >> 1;

    /**
     * 默认对象池 初始大小
     */
    private static final int DEFAULT_INITIAL_CAPACITY = 32;

    /**
     * 默认对象回收频率
     */
    private static final int DEFAULT_INITIAL_RECYCLE_INTERVAL = 8;


    // 回收频率 构造对象到一定次数才会回收对象
    // 例如： 0 则每次构建的对象都会回收
    //       1 则每第二次构造的对象才会回收
    private final int recycleInterval;

    /**
     * 队列初始大小
     */
    private final int initialCapacity;


    //池对象大小阈值  池中对象不会超过这个数值  默认 4096
    private final int maxPoolCapacity;

    public ObjectRecord() {
        this(DEFAULT_INITIAL_MAX_CAPACITY);
    }

    public ObjectRecord(int maxPoolCapacity) {
        this(maxPoolCapacity, DEFAULT_INITIAL_MAX, DEFAULT_INITIAL_RECYCLE_INTERVAL);
    }

    public ObjectRecord(int maxPoolCapacity, int initialCapacity, int recycleInterval) {
        this.recycleInterval = max(0, recycleInterval);
        if (maxPoolCapacity <= 0) {
            this.maxPoolCapacity = 0;
            this.initialCapacity = 0;
        } else {
            this.maxPoolCapacity = max(4, maxPoolCapacity);
            this.initialCapacity = min(DEFAULT_INITIAL_CAPACITY, initialCapacity);
        }
    }

    private static final ObjectHandle<?> NOOP_HANDLE = new ObjectHandle<>() {

        @Override
        public void recycle(Object o) {
            //NOOP
        }

        @Override
        public String toString() {
            return "NOOP";
        }
    };

    private final ThreadLocal<Pool<T>> threadLocalPool = new ThreadLocal<>() {
        @Override
        protected Pool<T> initialValue() {
            return new Pool<>(initialCapacity, maxPoolCapacity, recycleInterval);
        }

        @Override
        public void remove() {
            Pool<T> tPool = get();
            MessagePassingQueue<DefaultHandle<T>> handlers = tPool.handlers;
            tPool.handlers = null;
            handlers.clear();
            super.remove();
        }
    };

    final int currentPoolSize() {
        return threadLocalPool.get().handlers.size();
    }

    T get() {
        if (maxPoolCapacity == 0) {
            return newObject(TypeConvert.cast(NOOP_HANDLE));
        }
        Pool<T> localPool = threadLocalPool.get();
        DefaultHandle<T> handle = localPool.apply();
        T object;
        if (handle == null) {
            handle = localPool.newHandler();
            if (handle != null) {
                object = newObject(handle);
                handle.set(object);
            } else {
                object = newObject(TypeConvert.cast(NOOP_HANDLE));
            }
        } else {
            object = handle.get();
        }
        return object;
    }


    protected abstract T newObject(ObjectHandle<T> handle);

    private static final class DefaultHandle<T> implements ObjectHandle<T> {

        // 被申请时 处于该状态
        private static final int STATE_APPLY = 0;

        //被回收时 处于该状态
        private static final int STATE_AVAILABLE = 1;

        private static final AtomicIntegerFieldUpdater<DefaultHandle<?>> STATE_UPDATER;

        static {
            STATE_UPDATER = TypeConvert.cast(AtomicIntegerFieldUpdater.newUpdater(DefaultHandle.class, "state"));
        }

        private final Pool<T> pool;

        private T value;

        private volatile int state;

        public DefaultHandle(Pool<T> pool) {
            this.pool = pool;
        }

        @Override
        public void recycle(T o) {
            if (o != value) {
                throw new IllegalArgumentException("object does not belong to handle");
            }
            pool.release(this);
        }


        public T get() {
            return this.value;
        }


        public void set(T t) {
            this.value = t;
        }


        public void toAvailable() {
            int preState = STATE_UPDATER.getAndSet(this, STATE_AVAILABLE);
            if (preState == STATE_AVAILABLE) {
                throw new IllegalStateException("Object has been recycled already.");
            }
        }


        public boolean availableToApply() {
            if (state != STATE_AVAILABLE) {
                return false;
            }
            return STATE_UPDATER.compareAndSet(this, STATE_AVAILABLE, STATE_APPLY);
        }
    }


    private static final class Pool<T> {
        private volatile MessagePassingQueue<DefaultHandle<T>> handlers;
        private final int interval;
        private int calInterval1;

        public Pool(int initialCapacity, int maxCapacity, int interval) {
            this.interval = interval;
            this.handlers = new MpscChunkedAtomicArrayQueue<>(initialCapacity, maxCapacity);
            this.calInterval1 = interval;
        }

        void release(DefaultHandle<T> handler) {
            MessagePassingQueue<DefaultHandle<T>> queues = handlers;
            handler.toAvailable();
            if (queues != null) {
                queues.relaxedOffer(handler);
            }
        }

        DefaultHandle<T> apply() {
            MessagePassingQueue<DefaultHandle<T>> queue = handlers;
            if (queue == null) {
                return null;
            }
            DefaultHandle<T> handler;
            do {
                handler = handlers.relaxedPoll();
            } while (handler != null && !handler.availableToApply());
            return handler;
        }

        public DefaultHandle<T> newHandler() {
            if (++calInterval1 >= interval) {
                calInterval1 = 0;
                return new DefaultHandle<>(this);
            }
            return null;
        }
    }


    interface ObjectHandle<T> {
        void recycle(T o);
    }

}
