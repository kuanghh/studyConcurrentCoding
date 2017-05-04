package com.khh.part4.test_threadlocal;

/**
 * Created by 951087952@qq.com on 2017/5/4.
 * 验证线程变量的隔离性
 */
public class TestThreadLocal {
    private static ThreadLocal<String> t1 = new ThreadLocal<String>();

    static class ThreadA implements Runnable{

        @Override
        public void run() {
            try {
                for (int i = 0; i < 100; i++) {
                    String str = Thread.currentThread().getName() + i;
                    t1.set(str);
                    Thread.sleep(200);
                    System.out.println(str + " == " + t1.get() + " ? " + str.equals(t1.get()));
                    Thread.sleep(200);
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    static class ThreadB implements Runnable{
        @Override
        public void run() {
            try {
                for (int i = 0; i < 100; i++) {
                    String str = Thread.currentThread().getName() + i;
                    t1.set(str);
                    System.out.println(str + " == " + t1.get() + " ? " + str.equals(t1.get()));
                    Thread.sleep(200);
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread ta = new Thread(new ThreadA(),"ThreadA");
        Thread tb = new Thread(new ThreadB(),"ThreadB");
        ta.start();
        tb.start();
    }
}
