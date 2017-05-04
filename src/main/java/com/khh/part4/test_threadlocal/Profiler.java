package com.khh.part4.test_threadlocal;

/**
 * Created by 951087952@qq.com on 2017/5/4.
 * 测试 ThreadLocal的使用
 *
 * 下列程序输出：Cost : 1000 mills
 */
public class Profiler {

    private static final ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<Long>(){
        @Override
        protected Long initialValue() {
            return System.currentTimeMillis();
        }
    };

    public static final void begin(){
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }
    public static final long end(){
        return System.currentTimeMillis() - TIME_THREADLOCAL.get();
    }

    public static void main(String[] args) throws InterruptedException {

        Profiler.begin();
        Thread.sleep(1000);
        System.out.println("Cost : " + Profiler.end() + " mills ");

    }
}
