package com.khh.part1;

/**
 * Created by 951087952@qq.com on 2017/4/29.
 * 演示死锁，并查看dump线程
 * 在cmd 输入 jps 查看当前的LVMID(pid)  比如查询到了当前的pid为1712
 * 然后再输入 jstack -l 1712(当前的pid)
 *
 *  输出内容(部分)：
 *   Java stack information for the threads listed above:
     ===================================================
     "Thread-1":
     at com.khh.part1.DeadLockDemo$2.run(DeadLockDemo.java:37)
     - waiting to lock <0x00000007817eb608> (a java.lang.String)
     - locked <0x00000007817eb638> (a java.lang.String)
     at java.lang.Thread.run(Thread.java:745)
     "Thread-0":
     at com.khh.part1.DeadLockDemo$1.run(DeadLockDemo.java:28)
     - waiting to lock <0x00000007817eb638> (a java.lang.String)
     - locked <0x00000007817eb608> (a java.lang.String)
     at java.lang.Thread.run(Thread.java:745)
 *
 */
public class DeadLockDemo {

    private static String A = "A";
    private static String B = "B";
    public static void main(String[] args) {
        new DeadLockDemo().deadLock();
    }
    private void deadLock() {
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                synchronized (A) {
                    try { Thread.currentThread().sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (B) {
                        System.out.println("1");
                    }
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                synchronized (B) {
                    synchronized (A) {
                        System.out.println("2");
                    }
                }
            }
        });
        t1.start();
        t2.start();
    }
}
