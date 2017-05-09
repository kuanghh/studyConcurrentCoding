package com.khh.part5._lock;

import org.junit.Test;

import java.util.concurrent.locks.Lock;

/**
 * Created by 951087952@qq.com on 2017/5/9.
 * 测试TwinsLock锁
 *
 * 结果输出:
 *   Thread-0
     Thread-2

    代表同一时刻只有两个线程能获取到锁
 */
public class TestTwinsLock {

    @Test
    public void test() throws Exception{
        final Lock lock = new TwinsLock();

        class Worker extends  Thread{
            public void run(){
                while(true){
                    lock.lock();
                    try {
                        Thread.sleep(1000);
                        System.out.println(Thread.currentThread().getName());
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        //启动10个线程
        for (int i = 0; i < 10; i++) {
            Worker w = new Worker();
            w.setDaemon(true);
            w.start();
        }
        //每隔一秒换行
        for (int i = 0; i < 10; i++) {
            Thread.sleep(1000);
            System.out.println();
        }
    }
}
