package com.khh.part3;

/**
 * Created by 951087952@qq.com on 2017/5/2.
 *  volatile声明的一个变量后，java线程内存模型确保了所有线程看到这个变量的值是一致的
 *  volatile 一般情况下不能代替sychronized ，因为volatile不能保证操作的原子性
 *
 *  volatile关键字用于声明简单类型变量，如int、float、 boolean等数据类型。
 *  如果这些简单数据类型声明为volatile，对它们的操作就会变成原子级别的。但这有一定的限制
 *
 *  下面例子是一个   volatile不能代替sychronized的列子
 *  为什么会造成这样的现象？
 *
 *  原因是声明为volatile的简单变量如果当前值由该变量以前的值相关，那么volatile关键字不起作用，
 *  也就是说如下的表达式都不是原子操作：
 *    n  =  n  +   1 ;
 *    n ++ ;
 *
 */
public class VolatileExample {

    public volatile static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {Thread.sleep(3);} catch (InterruptedException e) {}//这里为了效果更明显
                    count++;
                }
            }).start();
        }

        Thread.sleep(5000);
        System.out.println(count); //输出经常不是1000
    }

}
