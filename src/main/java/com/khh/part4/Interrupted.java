package com.khh.part4;

/**
 * Created by 951087952@qq.com on 2017/5/3.
 * 理解中断
 *
 *  1.中断可以理解为一个标识为属性，表示一个运行中的线程是否被其他线程运行了中断操作
 *    其它线程通过调用该线程的interrupt()方法对其进行中断操作
 *
 *  2.线程可以通过方法isInterrupted()方法来进行判断是否被 中断
 *    也可以调用静态方法Thread.interrupted()对当前线程的中断表示为进行复位
 *
 *  3.如果该线程已经处于终结状态，即使该线程被中断过，在调用该线程对象的isInterrupted()方法时依旧返回false
 *
 *  4.许多声明抛出InterruptedException的方法，这些方法在抛出InterruptedException之前，JVM会将该线程的中断标识位
 *    清除，然后再抛出InterruptedException，此时调用isInterrupted()方法将会得到false
 */
public class Interrupted {

    static class SleepRunner implements  Runnable{

        @Override
        public void run() {
            while(true){
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class BusyRunner implements  Runnable{

        @Override
        public void run() {
            while(true){
            }
        }
    }

    public static void main(String[] args) throws Exception{
        //sleepThread 不停的尝试睡眠
        Thread sleepThread = new Thread(new SleepRunner(), "SleepThread");
        sleepThread.setDaemon(true);
        //busyThread不停的运行
        Thread busyThread = new Thread(new BusyRunner(), "BusyRunner");
        busyThread.setDaemon(true);

        sleepThread.start();
        busyThread.start();

        //休眠5秒，让sleepThread和busyThread充分运行
        Thread.sleep(5000);

        sleepThread.interrupt();
        busyThread.interrupt();

        System.out.println("SleepThread interrupted is " + sleepThread.isInterrupted());
        System.out.println("BusyThread interrupted is " + busyThread.isInterrupted());
        //防止sleepThread 和 busyThread 立刻退出
        Thread.sleep(2000);

    }

    /**
     * 最后打印出
         SleepThread interrupted is false
         BusyThread interrupted is true
             java.lang.InterruptedException: sleep interrupted
             at java.lang.Thread.sleep(Native Method)
             at com.khh.part4.Interrupted$SleepRunner.run(Interrupted.java:15)
             at java.lang.Thread.run(Thread.java:745)
     */
}
