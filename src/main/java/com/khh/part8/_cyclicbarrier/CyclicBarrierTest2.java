package com.khh.part8._cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by 951087952@qq.com on 2017/5/11.
 *
 * CyclicBarrier还提供一个更高级的构造函数CyclicBarrier（int parties，Runnable barrier-Action），
 * 用于在线程到达屏障时，优先执行barrierAction，
 *
 * 输出结果:
 *  3
 *  1
 *  2
 *  或者
 *  3
 *  2
 *  1
 */
public class CyclicBarrierTest2 {

    static CyclicBarrier c = new CyclicBarrier(2,new A());

    static class A implements Runnable{
        @Override
        public void run() {
            System.out.println(3);
        }
    }

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    c.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(1);
            }
        }).start();
        try {
            c.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(2);
    }


}
