package com.khh.part5.read_write_lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * Created by 951087952@qq.com on 2017/5/9.
 * 使用 ReenTrantReadWriteLock 来写一个缓存实例，展示  读写锁  的使用方式
 */
public class Cache {
    //一个用来存储数据的HashMap
    private static Map<String,Object> map = new HashMap<String,Object>();

    //读写锁
    private static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    //读锁
    private static Lock r = rwl.readLock();

    //写锁
    private static Lock w = rwl.writeLock();

    public static final Object get(String key){

        r.lock();
        try{
            return map.get(key);
        }finally {
            r.unlock();
        }
    }

    /**
     * 设置key对应的value，并放回旧的value
     * @param key
     * @param value
     * @return
     */
    public static final Object set(String key,Object value){
        w.lock();
        try{
           return map.put(key,value);
        }finally {
            w.unlock();
        }
    }

    /**
     * 清空所有内容
     */
    public static final void clear(){
        w.lock();
        try{
            map.clear();
        }finally {
            w.unlock();
        }
    }
}
