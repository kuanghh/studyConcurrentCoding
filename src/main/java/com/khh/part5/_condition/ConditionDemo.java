package com.khh.part5._condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by 951087952@qq.com on 2017/5/10.
 * Condition 使用示例
 */
public class ConditionDemo {

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void conditionWait() throws InterruptedException{
        lock.lock();
        try{
            condition.await();
        }finally {
            lock.unlock();
        }
    }

    public void conditionSignal() throws InterruptedException{
        lock.lock();
        try{
            condition.signal();
        }finally {
            lock.unlock();
        }
    }
}
