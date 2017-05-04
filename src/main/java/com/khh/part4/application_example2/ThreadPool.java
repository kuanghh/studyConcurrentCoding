package com.khh.part4.application_example2;

/**
 * Created by 951087952@qq.com on 2017/5/4.
 * 模拟一个简单的线程池接口
 */
public interface ThreadPool<Job extends Runnable> {

    //执行一个Job 这个Job需要实现Runnable接口
    void execute(Job job);

    //关闭线程池
    void shutdown();

    //增加工作者线程
    void addWorkers(int num);

    //减少工作者线程
    void removeWorker(int num);

    //得到正在执行的任务数量
    int getJobSize();
}
