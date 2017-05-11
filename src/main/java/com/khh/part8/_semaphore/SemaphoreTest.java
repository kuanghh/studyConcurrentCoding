package com.khh.part8._semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by 951087952@qq.com on 2017/5/11.
 * Semaphore（信号量）是用来控制同时访问特定资源的线程数量，它通过协调各个线程，以保证合理的使用公共资源。
 *
 * 在代码中，虽然有30个线程在执行，但是只允许10个并发执行。Semaphore的构造方法Semaphore（int permits）接受一个整型的数字，
 * 表示可用的许可证数量。Semaphore（10）表示允许10个线程获取许可证，也就是最大并发数是10。
 * Semaphore的用法也很简单，首先线程使用Semaphore的acquire()方法获取一个许可证，
 * 使用完之后调用release()方法归还许可证。还可以用tryAcquire()方法尝试获取许可证
 *
 * Semaphore还提供一些其他方法，具体如下。
 *    ·intavailablePermits()：返回此信号量中当前可用的许可证数。
 *    ·intgetQueueLength()：返回正在等待获取许可证的线程数。
 *    ·booleanhasQueuedThreads()：是否有线程正在等待获取许可证。
 *    ·void reducePermits（int reduction）：减少reduction个许可证，是个protected方法。
 *    ·Collection getQueuedThreads()：返回所有等待获取许可证的线程集合，是个protected方法。
 */
public class SemaphoreTest {
    private static final int THREAD_COUNT = 30;

    private static ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT);

    private static Semaphore s = new Semaphore(10);

    public static void main(String[] args) {
        for (int i = 0; i < THREAD_COUNT; i++) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        s.acquire();
                        System.out.println("save data");
                        s.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        threadPool.shutdown();
    }

}
