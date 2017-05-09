package com.khh.part5._lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Created by 951087952@qq.com on 2017/5/9.
 *
 * 自定义个一个 只允许至多两个线程同时访问，超过两个线程的访问将被阻塞 的锁
 *
 * 因为支持多个线程访问，所以是共享式访问，需要使用同步器提供的acquireShared(int args )方法等和Shared相关的方法
 *
 * 这就要求TwinsLock必须重写tryAcquireShared(int args)和tryReleaseShared（int args） 方法
 * ,这样才能保证同步器的共享式同步状态的获取与释放方法得以执行
 */
public class TwinsLock implements Lock{

    /**
     * 一开始Sync sync = new Sync(2); 代表同步资源数为2即status为2
     */
    private final Sync sync = new Sync(2);

    private static final class Sync extends AbstractQueuedSynchronizer{
        Sync(int count){
            if(count <= 0){
                throw new IllegalArgumentException("count must large than zero");
            }
            setState(count);
        }

        /**
         * 当一个线程获取，status减1
         * @param reduceCount
         * @return
         */
        @Override
        protected int tryAcquireShared(int reduceCount) {
            for(;;){
                int current = getState();
                int newCount = current - reduceCount;
                if(newCount < 0 || compareAndSetState(current,newCount)){
                    return newCount;
                }
            }
        }

        /**
         * 当一个线程释放，status加一
         * @return
         */
        @Override
        protected boolean tryReleaseShared(int returnCount) {
            for(;;){
                int current = getState();
                int newCount = current + returnCount;
                if(compareAndSetState(current,newCount)){
                    return true;
                }
            }
        }
        //返回一个Condition ，每个condition都包含一个condition队列
        Condition newCondition(){
            return new ConditionObject();
        }
    }

    @Override
    public void lock() {
        sync.acquireShared(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquireShared(1) > 0;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireSharedNanos(1,time);
    }

    @Override
    public void unlock() {
        sync.releaseShared(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}
