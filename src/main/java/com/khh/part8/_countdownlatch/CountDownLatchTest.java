package com.khh.part8._countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * Created by 951087952@qq.com on 2017/5/11.
 * 测试CountDownLatch
 *
 * CountDownLatch也可以实现join的功能，并且比join的功能更多
 *
 * CountDownLatch的构造函数接收一个int类型的参数作为计数器，如果你想等待N个点完成，这里就传入N。
 *
 * 当我们调用CountDownLatch的countDown方法时，N就会减1，CountDownLatch的await方法
 * 会阻塞当前线程，直到N变成零。由于countDown方法可以用在任何地方，所以这里说的N个
 * 点，可以是N个线程，也可以是1个线程里的N个执行步骤。用在多个线程时，只需要把这个
 * CountDownLatch的引用传递到线程里即可。
 *
 * 计数器必须大于等于0，只是等于0时候，计数器就是零，调用await方法时不会阻塞当前线程
 *
 * 输出:
 *  1
 *  2
 *  3
 */
public class CountDownLatchTest {
    static CountDownLatch c = new CountDownLatch(2);

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(1);
                c.countDown();
                System.out.println(2);
                c.countDown();
            }
        }).start();
        c.await();
        System.out.println(3);
    }
}
