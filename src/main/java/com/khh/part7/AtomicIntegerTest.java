package com.khh.part7;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by 951087952@qq.com on 2017/5/10.
 * 测试AtomicInteger，其用法与AtomicBoolean、AtomicLong基本一样,都属于  原子更新基本类型类
 *
 * 输出:
 *  1
 *  2
 */
public class AtomicIntegerTest {

    static AtomicInteger ai = new AtomicInteger(1);
    public static void main(String[] args) {
        System.out.println(ai.getAndIncrement());
        System.out.println(ai.get());
    }
}
