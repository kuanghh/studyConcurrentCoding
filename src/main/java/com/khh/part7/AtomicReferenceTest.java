package com.khh.part7;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by 951087952@qq.com on 2017/5/10.
 * 测试AtomicReference，其用法与AtomicReferenceFieldUpdater，AtomicMarkableReference几乎一样，都属于原子更新引用类型
 *
 * 输出：
 *  Tom
 *  17
 */
public class AtomicReferenceTest {

    public static AtomicReference<User> atomicReference = new AtomicReference<>();

    static class User{
        private String name;
        private int old;
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

    public static void main(String[] args) {
        User user = new User("conan",15);
        atomicReference.set(user);
        User updateUser = new User("Tom",17);
        atomicReference.compareAndSet(user,updateUser);
        System.out.println(atomicReference.get().getName());
        System.out.println(atomicReference.get().getOld());
    }


}
