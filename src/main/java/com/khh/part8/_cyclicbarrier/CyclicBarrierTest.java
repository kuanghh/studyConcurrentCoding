package com.khh.part8._cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by 951087952@qq.com on 2017/5/11.
 * CyclicBarrier的字面意思是可循环使用（Cyclic）的屏障（Barrier）。
 * 它要做的事情是，让一组线程到达一个屏障（也可以叫同步点）时被阻塞，
 * 直到最后一个线程到达屏障时，屏障才会开门，所有被屏障拦截的线程才会继续运行。
 *
 * CyclicBarrier默认的构造方法是CyclicBarrier（int parties），其参数表示屏障拦截的线程数量，
 * 每个线程调用await方法告诉CyclicBarrier我已经到达了屏障，然后当前线程被阻塞。
 *
 * 如果把new CyclicBarrier(2)修改成new CyclicBarrier(3)，则主线程和子线程会永远等待，
 * 因为没有第三个线程执行await方法，即没有第三个线程到达屏障，所以之前到达屏障的两个线程都不会继续执行。
 *
 * 输出结果:
 *  1
 *  2
 *  或者
 *  2
 *  1
 *  因为是同时唤醒两条线程，所以两个线程都有可能先执行
 *
 */
public class CyclicBarrierTest {

    static CyclicBarrier c = new CyclicBarrier(2);

    public static void main(String[] args) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    c.await();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                System.out.println(1);
//            }
//        }).start();
        new Thread(() ->{
            try {
                c.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(1);
        }).start();

        try {
            c.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(2);
    }
}
