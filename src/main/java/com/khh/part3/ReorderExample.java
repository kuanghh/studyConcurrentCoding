package com.khh.part3;


import java.nio.file.*;

/**
 * Created by 951087952@qq.com on 2017/5/2.
 * 重排序对多线程的影响
 *
 *  在多线程环境中，如果有一条线程执行wirter()，一条线程执行reader(),那么，在reader中，
 *  有可能（但几率非常低，与cpu编译器有关）进入了if（即flag ==true）后，输出的i是0
 *
 *  原因是在执行writer时，语句 a=1和 语句 flag = true 发生了重排序，导致了，先执行后者，
 *  此时另一条线程执行了reader方法，导致进入了if语句后，读出来的a还是0
 *
 *  虽然发生的概率非常非常的低，下面的main方法，测试了很多次，依然得不到想要的结果，但这个可能性依然存在
 *
 *  为了解决这个问题，可以通过  在writer方法和reader方法，加上synchronized,使其同步，则可以避免
 */
public class ReorderExample {
    int a = 0;
    boolean flag = false;
    public  void writer()throws Exception{
        a = 1;
        flag = true;
    }
    public void reader()throws Exception{
        if(flag){
            if(a == 0){
                writerTofile("a===0");
            }
            int i = a * a;
            System.out.println(i);
        }
    }

    public static void writerTofile(String context) throws Exception{
        Path path = Paths.get("E:/1.txt");
        if(!path.toFile().exists()){
            path = Files.createFile(path);
        }
        Files.write(path,context.getBytes(), StandardOpenOption.APPEND);
    }


    public static void main(String[] args) throws Exception{
        //创建两条线程还有一个对象
        final ReorderExample reorderExample = new ReorderExample();

        for (int i = 0; i < 100000; i++) {
            Thread thread1 = new Thread(new Runnable() {
                public void run() {
                    try {
                        reorderExample.writer();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            Thread thread2 = new Thread(new Runnable() {
                public void run() {
                    try {
                        reorderExample.reader();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
            thread1.start();
            thread2.start();
            thread1.join();
            thread2.join();
            reorderExample.a = 0;
            reorderExample.flag = false;
        }
    }
}
