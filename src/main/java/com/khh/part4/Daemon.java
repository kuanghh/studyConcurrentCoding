package com.khh.part4;

import java.util.concurrent.TimeUnit;

/**
 * Created by 951087952@qq.com on 2017/5/3.
 * Daemon线程是一种支持性线程，因为它主要被用作程序后台调度以及支持性工作
 * 这意味着，当一个java虚拟机中不存在非Daemon线程的时候，java虚拟机将会退出
 * 可以通过调用Thread.setDaemon（true）将线程设置为Daemon线程
 *
 * Daemon属性需要在启动线程之前设置，不能在启动线程之后设置
 *
 * Daemon线程被用作完成支持性工作，但是在java虚拟机退出时Daemon线程中的finally块并不一定执行，例子如下
 */
public class Daemon {
    static class DaemonRunner implements Runnable{

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                System.out.println("DaemonThread finally run");
            }
        }
    }
    public static void main(String[] args) {
        Thread thread = new Thread(new DaemonRunner(),"DaemonRunner");
        thread.setDaemon(true);
        thread.start();

        //程序结果并没有输入任何东西
    }
}
