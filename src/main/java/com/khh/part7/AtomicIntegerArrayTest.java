package com.khh.part7;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * Created by 951087952@qq.com on 2017/5/10.
 * 测试AtomicIntegerArray，
 * 其用法与AtomicLongArray,AtomicReferenceArray,AtomicBooleanArray几乎一样，它们都属于原子更新数组类
 *
 * 输出：
 *  3
 *  1
 *
 *  注意:数组value通过构造方法传递进去，然后AtomicIntegerArray会将当前数组复制一份，
 *  所以当AtomicIntegerArray对内部的数组元素进行修改时，不会影响传入的数组
 */
public class AtomicIntegerArrayTest {
    static int[] value = new int[]{1,2};
    static AtomicIntegerArray ai = new AtomicIntegerArray(value);

    public static void main(String[] args) {
        ai.getAndSet(0,3);
        System.out.println(ai.get(0));
        System.out.println(value[0]);
    }
}
