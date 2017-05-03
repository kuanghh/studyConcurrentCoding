package com.khh.part3;

/**
 * Created by 951087952@qq.com on 2017/5/3.
 * 基于volatile 的双重检查锁
 */
public class SafeDoubleCheckedLocking {

    private SafeDoubleCheckedLocking(){}

    private volatile static SafeDoubleCheckedLocking instance;
    public static SafeDoubleCheckedLocking getInstance(){
        if(instance == null){
            synchronized (SafeDoubleCheckedLocking.class){
                if(instance == null){
                    instance = new SafeDoubleCheckedLocking();   //此时instance为volatile，就没事了
                }
            }
        }
        return instance;
    }
}
