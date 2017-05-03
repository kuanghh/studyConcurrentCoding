package com.khh.part4;

import org.junit.Test;

/**
 * Created by 951087952@qq.com on 2017/5/3.
 * 测试 interrupt  isInterrupted  Thread.interrupted()  方法
 *
 * 下面测试的输出结果为:
 *
 *   主线程名字为 = main
     进入了线程 : thread1
     thread1 isInterrupted = true
     当前线程被其它线程中断了
     复位是否成功 : true
     thread1线程的中断标识为 : false

    过程分析:
      首先在main线程中，创建了一条名字为"thread1"的线程,然后start它，再让主线程(main)睡眠2秒
      此时thread1线程在不断的跑while，等主线程醒来后，主线程中断了thread1线程(其实是标识thread1中断状态)
      接着，thread1线程中的while方法里，通过thread.isInterrupted()，检测到了当前线程被其它线程中断了，并输出一段文字，
      最后，通过Thread.interrupted()方法，让当前线程(thread1线程)的中断标识位复位(也就是thread.isInterrupted()的结果true --> false)
 */
public class TestInterrupted {

    @Test
    public void test1() throws Exception{
        Thread mainThread = Thread.currentThread();
        System.out.println("主线程名字为 = " + mainThread.getName());

        Thread thread1 = new Thread(new Runnable(){

            public void run() {
                Thread thread = Thread.currentThread();
                System.out.println("进入了线程 : " + thread.getName());
                while(true) {
                    //检测当前线程是否被其它线程中断了
                    if(thread.isInterrupted()){
                        System.out.println("当前线程被其它线程中断了");
                        //复位
                        boolean interrupted = Thread.interrupted();
                        System.out.println("复位是否成功 : " + interrupted );
                        System.out.println("thread1线程的中断标识为 : " + thread.isInterrupted());
                        break;
                    }
                }

            }
        },"thread1");
        thread1.start();

        //让thread1充分运行
        Thread.sleep(2000);
        //中断thread1
        thread1.interrupt();
        System.out.println("thread1 isInterrupted = " + thread1.isInterrupted());
    }

}
