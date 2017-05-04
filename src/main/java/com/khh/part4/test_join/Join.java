package com.khh.part4.test_join;

/**
 * Created by 951087952@qq.com on 2017/5/4.
 * 测试 Join
 *
 * 如果一个线程A执行了thread.join()语句，其含义是:当前线程A等待thread线程终止之后才从thread.join()返回
 *
 * 下面 程序输出:
 *  main main terminate.
     0 terminate.
     1 terminate.
     2 terminate.
     3 terminate.
     4 terminate.
     5 terminate.
     6 terminate.
     7 terminate.
     8 terminate.
     9 terminate.
 */
public class Join {

    static class Domino implements Runnable{
        private Thread thread;
        public Domino(Thread thread){
            this.thread = thread;
        }
        @Override
        public void run() {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " terminate.");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread privous = Thread.currentThread();
        for (int i = 0; i < 10; i++) {
            //每个线程拥有前一个线程的引用，需要等待前一个线程终止，才能从等待中返回
            Thread thread = new Thread(new Domino(privous),String.valueOf(i));
            thread.start();
            privous = thread;
        }
        Thread.sleep(5000);
        System.out.println(" main " + Thread.currentThread().getName() + " terminate.");
    }
}
