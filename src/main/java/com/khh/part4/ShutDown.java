package com.khh.part4;

/**
 * Created by 951087952@qq.com on 2017/5/3.
 * 如何安全的终止线程
 *
 * 下面代码输出结果（每次结果不一定相同）:
 *      当前线程 ：Thread-one------- Count i = 105789833
        当前线程 ：Thread-two------- Count i = 115044
 */
public class ShutDown {

    private static class Runner implements Runnable{
        private long i;
        private volatile boolean on = true;

        @Override
        public void run() {
            while(on && !Thread.currentThread().isInterrupted()){
                i++;
            }
            System.out.println("当前线程 ：" + Thread.currentThread().getName() + "-------" + " Count i = " + i);
        }

        public void cancel(){
            on = false;
        }
    }

    public static void main(String[] args) throws Exception{

        /* ======================================安全终止线程方案1=========================================== */
        Runner one = new Runner();
        Thread thread1 = new Thread(one,"Thread-one");
        thread1.start();
        //睡眠1秒
        Thread.sleep(1000);
        thread1.interrupt();
        /* ======================================安全终止线程方案1=========================================== */

        /* ======================================安全终止线程方案2=========================================== */
        Runner two = new Runner();
        Thread thread2 = new Thread(two,"Thread-two");
        thread2.start();
        //睡眠1秒
        Thread.sleep(1000);
        two.cancel();
        /* ======================================安全终止线程方案2=========================================== */

    }
}
