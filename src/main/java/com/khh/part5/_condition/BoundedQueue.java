package com.khh.part5._condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by 951087952@qq.com on 2017/5/10.
 * 使用Condition 实现有界对列的实现
 * 当队列为空，队列的获取操作将会阻塞获取线程，知道队列中有新增元素，当队列已满时，队列的插入操作将会阻塞插入线程，知道队列出现"空位"
 */
public class BoundedQueue<T> {
    private Object[] items;
    //添加的下标，删除的下标和数组当前的数量
    private int addIndex,removeIndex,count;

    private Lock lock = new ReentrantLock();
    private Condition notEmpty = lock.newCondition();
    private Condition notFull = lock.newCondition();

    public BoundedQueue(int size){
        items = new Object[size];
    }

    /**
     * 添加一个元素，如果数组满，则添加线程进入等待状态，直到有"空位"
     * @param t
     * @throws InterruptedException
     */
    public void add(T t) throws InterruptedException{
        lock.lock();
        try{
            //数组满了
            while(count == items.length){
                notFull.await();
            }
            items[addIndex] = t;
            if(++addIndex == items.length){
                addIndex = 0;
            }
            ++count;
            notEmpty.notify();
        }finally {
            lock.unlock();
        }
    }

    /**
     * 由头部删除一个元素，如果数组为空，则删除线程进入等待状态，知道有新添加元素
     * @return
     * @throws InterruptedException
     */
    public T remove() throws InterruptedException{
        lock.lock();
        try{
            //数组为空
            while(count == 0){
                notEmpty.await();
            }
            Object item = items[removeIndex];
            if(++removeIndex == items.length){
                removeIndex = 0;
            }
            --count;
            notFull.notify();
            return (T) item;
        }finally {
            lock.unlock();
        }
    }
}
