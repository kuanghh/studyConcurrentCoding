package com.khh.part7;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * Created by 951087952@qq.com on 2017/5/10.
 * 测试AstomicIntegerFieldUpdater，其用法与AtomicLongFieldUpdater，AtomicStampedReference几乎一样，都属于原子更新字段类
 *
 * ·AtomicIntegerFieldUpdater：原子更新整型的字段的更新器。
 * ·AtomicLongFieldUpdater：原子更新长整型字段的更新器。
 * ·AtomicStampedReference：原子更新带有版本号的引用类型。该类将整数值与引用关联起来，
 *      可用于原子的更新数据和数据的版本号，可以解决使用CAS进行原子更新时可能出现的ABA问题
 *
 *
 * 要想原子地更新字段类需要两步。
 *  第一步，因为原子更新字段类都是抽象类，每次使用的时候必须使用静态方法newUpdater()创建一个更新器，并且需要设置想要更新的类和属性。
 *
 *  第二步，更新类的字段（属性）必须使用public volatile修饰符。
 *
 *
 *  代码输出结果:
 *      10
 *      11
 */
public class AtomicIntegerFieldUpdaterTest {

    //创建原子更新器，并设置需要更新的对象类和对象的属性
    private static AtomicIntegerFieldUpdater<User> a = AtomicIntegerFieldUpdater.newUpdater(User.class,"old");

    public static void main(String[] args) {
        //设置柯南的年龄是10岁
        User conan = new User("conan",10);
        //柯南长一岁，但是仍然会输出旧的年龄
        System.out.println(a.getAndIncrement(conan));
        //输出柯南现在的年龄
        System.out.println(a.get(conan));
    }

    static class User{
        private String name;
        private volatile int old;
        public User(String name, int old) {
            this.name = name;
            this.old = old;
        }
        public String getName() {
            return name;
        }
        public int getOld() {
            return old;
        }
    }
}
