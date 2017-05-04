package com.khh.part4.application_example;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by 951087952@qq.com on 2017/5/4.
 *  测试
 *  main 方法模拟了
 *   从连接池中获取 threadCount（线程数） * count(每天线程从连接池获取连接的数量) 连接，有多少连接可以获取到，有多少连接获取超时
 *
 *
 */
public class ConnectionPoolTest {
    //创建具有10条连接的连接池
    static ConnectionPool pool = new ConnectionPool(10);

    //保证所有ConnectionRunner 能够同时开始
    static CountDownLatch start = new CountDownLatch(1);

    //main线程将会等待所有ConnectionRunner结束后才能继续执行
    static CountDownLatch end;

    static class ConnectionRunner implements Runnable{

        int count;
        AtomicInteger got;
        AtomicInteger notGot;
        public ConnectionRunner(int count,AtomicInteger got,AtomicInteger notGot){
            this.count = count;
            this.got = got;
            this.notGot = notGot;
        }

        @Override
        public void run() {
            try {
                start.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while(count > 0){
                try {
                    //从线程池中获取连接，如果1秒内无法获取到，将返回null
                    //分别统计连接获取的数量got和未获取到的数量notGot
                    Connection connection = pool.fetchConnection(1000);
                    if(connection != null){
                        try {
                            connection.createStatement();
                            connection.commit();
                        } finally {
                            pool.releaseConnection(connection);
                            got.incrementAndGet();
                        }
                    }else{
                        notGot.incrementAndGet();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    count --;
                }
            }
            end.countDown();
        }
    }

    public static void main(String[] args) throws Exception{
        //线程数量，可以修改线程数量进行观察
        int threadCount = 100;
        end = new CountDownLatch(threadCount);

        int count = 20;
        AtomicInteger got = new AtomicInteger();
        AtomicInteger notGot = new AtomicInteger();

        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(new ConnectionRunner(count,got,notGot),"ConnectionRunnerThread" + i);
            thread.start();
        }
        start.countDown();
        end.await();
        System.out.println("total invoke: " + (threadCount * count));
        System.out.println("got connection : " + got);
        System.out.println("notGot connection : " + notGot);
    }
}
