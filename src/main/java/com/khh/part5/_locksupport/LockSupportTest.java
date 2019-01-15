package com.khh.part5._locksupport;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by 951087952@qq.com on 2017/5/9.
 * 测试一下LockSupportApi
 *
 *     LockSupport.park(); 用来阻塞当前线程
 *     LockSupport.unpark(Thread thread)唤醒某个线程
 *     LockSupport.parkNanos(long nanos)
 *          阻塞当前线程，最长不超过nanos纳秒，返回条件在park()的基础上增加了超时返回
 *     LockSupport.parkUntil(long deadline)
 *          阻塞当前线程，直到deadline时间(从1970年开始到deadline时间的毫秒数)
 *
 */
public class LockSupportTest {

    static class RunnerA implements Runnable{
        @Override
        public void run() {
            System.out.println("A run");
            System.out.println("A stop");
            LockSupport.park();
            //LockSupport.park(Object blocker)
            //LockSupport.parkNanos(1000);
            //LockSupport.parkNanos(Object blocker,long nanos)
            //LockSupport.parkUntil(System.currentTimeMillis()+1000);
            // LockSupport.parkUntil(Object blocker,long deadline)
            System.out.println("A awake");
        }
    }

    static class RunnerB implements Runnable{
        private Thread thread;
        public RunnerB(Thread thread){
            this.thread = thread;
        }
        @Override
        public void run() {
            System.out.println("B notify A");
            LockSupport.unpark(thread);
        }
    }


    public static void main(String[] args) {
        Thread ta = new Thread(new RunnerA());
        Thread tb = new Thread(new RunnerB(ta));
        ta.start();
        tb.start();
    }

}
