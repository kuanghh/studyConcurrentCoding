package com.khh.part4.test_wait_notify;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 951087952@qq.com on 2017/5/4.
 *
 * 测试 wait   notify
 *
 * main 函数输出:
 *   Thread[WaitThread,5,main] flag is true.wait @ 13:58:24
     Thread[NotifyThread,5,main] hold lock.notify @ 13:58:25
     Thread[NotifyThread,5,main] hold lock.again @ 13:58:30
     Thread[WaitThread,5,main] flag is false.wait @ 13:58:35

 *      等待/通知的经典范式
 *
 *      等待方：
 *          synchronized(对象) {
 *              while(条件不满足){
 *                  对象.wait()
 *              }
 *              条件满足时候的对应的处理逻辑
 *          }
 *       通知方:
 *          synchronized(对象) {
 *              改变条件
 *              对象.notify() or 对象.notifyAll()
 *          }
 */
public class WaitNotify {

    static boolean flag = true;
    static Object lock = new Object();


    static class Wait implements Runnable{

        @Override
        public void run() {
            //加锁，拥有lock的Monitor
            synchronized (lock){
                while(flag){
                    try{
                        System.out.println(Thread.currentThread() + " flag is true.wait @ "
                                + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                        lock.wait();
                    }catch (InterruptedException e){
                        //do something
                    }
                }
                //条件满足时，输出下列工作
                System.out.println(Thread.currentThread() + " flag is false.wait @ "
                        + new SimpleDateFormat("HH:mm:ss").format(new Date()));
            }
        }
    }

    static class Notify implements  Runnable{

        @Override
        public void run() {
            //加锁，拥有lock的Monitor
            synchronized (lock){
                //获取lock的锁，然后进行通知，通知时不会释放lock的锁
                // 直到当前线程释放了lock后，WaitThread才能从wait方法返回
                System.out.println(Thread.currentThread() + " hold lock.notify @ "
                        + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                lock.notify();
                flag = false;
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //再次加锁
            synchronized (lock){
                System.out.println(Thread.currentThread() + " hold lock.again @ "
                        + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws  Exception{
        Thread waitThread = new Thread(new Wait(),"WaitThread");
        waitThread.start();
        Thread.sleep(1000);
        Thread notifyThread = new Thread(new Notify(),"NotifyThread");
        notifyThread.start();
    }

}
