package com.khh.part3;

/**
 * Created by 951087952@qq.com on 2017/5/3.
 * 双重检查锁定(这是一个错误的优化)
 *
 * 为什么这是一个错误的优化？
 *  在注释7那里instance = new DoubleCheckedLocking()
 *          可以分解为下列三行代码
 *              memory = allocate();//1:分配对象的内存空间
 *              ctorInstance(memory);//2:初始化对象
 *              instance = memory;  //3设置instance指向刚分配的内存地址
 *      在这三行代码中，在某一些JIT编译器上2，3行代码会进行重排序
 *      那么可能发生一种情况，
 *      当线程A执行了memory = allocate();instance = memory; 时，
 *      线程B调用判断if(instance == null)为false，直接并返回instance，这样instance对象尚为初始化
 *
 * 所以此优化是不对的
 *
 * 正确的优化方式请看SafeDoubleCheckedLocking,InstanceFactory
 */
public class DoubleCheckedLocking {

    private DoubleCheckedLocking(){}                                //1
    private static DoubleCheckedLocking instance;                   //2
    public static DoubleCheckedLocking getInstance(){               //3
        if(instance == null){                                       //4 第一次检查
            synchronized (DoubleCheckedLocking.class){              //5 加锁
                if(instance == null){                               //6 第二次检查
                    instance = new DoubleCheckedLocking();          //7 问题的根源出在这里
                }
            }
        }
        return instance;
    }
}
