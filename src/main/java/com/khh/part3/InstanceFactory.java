package com.khh.part3;

/**
 * Created by 951087952@qq.com on 2017/5/3.
 * JVM在类的初始化阶段(即在Class被加载后，且被线程使用之前)，会执行类的初始化。在执行类的初始化期间，JVM会获取一个锁
 * 这个锁可以同步多个线程对同一个类的初始化
 */
public class InstanceFactory {
    private InstanceFactory(){}

    private static class InstanceHolder{
        public static InstanceFactory instanceFactory;
    }
    public static InstanceFactory getInstance(){
        return InstanceHolder.instanceFactory;
    }

}
