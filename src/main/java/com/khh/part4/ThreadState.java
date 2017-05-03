package com.khh.part4;

/**
 * Created by 951087952@qq.com on 2017/5/3.
 * 配合 jstack工具查看线程状态
 *
 * //部分输出如下：
 *
 *   //BlockedThread-2线程阻塞在获取Blocked.class示例的锁上
 *   "BlockedThread-2" #14 prio=5 os_prio=0 tid=0x000000001ad74000 nid=0x1624 waiting
     for monitor entry [0x000000001c3ef000]
     java.lang.Thread.State: BLOCKED (on object monitor)
     at com.khh.part4.ThreadState$Blocked.run(ThreadState.java:54)
     - waiting to lock <0x00000007817f2938> (a java.lang.Class for com.khh.part4.ThreadState$Blocked)
     at java.lang.Thread.run(Thread.java:745)

      //BlockedThread-1线程获得Blocked.class锁，处于超时等待状态
     "BlockedThread-1" #13 prio=5 os_prio=0 tid=0x000000001ad63800 nid=0xed8 waiting
     on condition [0x000000001c1ef000]
     java.lang.Thread.State: TIMED_WAITING (sleeping)
     at java.lang.Thread.sleep(Native Method)
     at com.khh.part4.ThreadState$Blocked.run(ThreadState.java:54)
     - locked <0x00000007817f2938> (a java.lang.Class for com.khh.part4.ThreadState$Blocked)
     at java.lang.Thread.run(Thread.java:745)

      //WaitingThread线程在Waiting实例上等待
     "WaitingThread" #12 prio=5 os_prio=0 tid=0x000000001ad62800 nid=0x1214 in Object.wait() [0x000000001bf9f000]
     java.lang.Thread.State: WAITING (on object monitor)
     at java.lang.Object.wait(Native Method)
     - waiting on <0x00000007817f03c8> (a java.lang.Class for com.khh.part4.ThreadState$Waiting)
     at java.lang.Object.wait(Object.java:502)
     at com.khh.part4.ThreadState$Waiting.run(ThreadState.java:38)
     - locked <0x00000007817f03c8> (a java.lang.Class for com.khh.part4.ThreadState$Waiting)
     at java.lang.Thread.run(Thread.java:745)

      //TimeWaitingThread线程处于超时等待
     "TimeWaitingThread" #11 prio=5 os_prio=0 tid=0x000000001ad61800 nid=0x500 waiting on condition [0x000000001bd8f000]
     java.lang.Thread.State: TIMED_WAITING (sleeping)
     at java.lang.Thread.sleep(Native Method)
     at com.khh.part4.ThreadState$TimeWaiting.run(ThreadState.java:23)
     at java.lang.Thread.run(Thread.java:745)
 */

public class ThreadState {

    public static void main(String[] args) {
        new Thread(new TimeWaiting(),"TimeWaitingThread").start();
        new Thread(new Waiting(),"WaitingThread").start();
        //使用两个Blocked线程，一个获取锁成功，另一个阻塞
        new Thread(new Blocked(),"BlockedThread-1").start();
        new Thread(new Blocked(),"BlockedThread-2").start();
    }

    //该线程不断睡眠
    static class TimeWaiting implements Runnable{
        @Override
        public void run() {
            while(true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //该线程在Waiting.class实例上等待
    static class Waiting implements Runnable{
        @Override
        public void run() {
            while(true){
                synchronized (Waiting.class){
                    try{
                        Waiting.class.wait();
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    //该线程在Blocked.class实例上加锁后，不会释放该锁
    static class Blocked implements Runnable{
        @Override
        public void run() {
            synchronized (Blocked.class){
                while (true){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
