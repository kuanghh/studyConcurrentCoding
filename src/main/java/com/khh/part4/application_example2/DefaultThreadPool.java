package com.khh.part4.application_example2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by 951087952@qq.com on 2017/5/4.
 * 模拟线程池的实现
 *
 *  从代码可以看到，当客户调用execute(Job方法时)，会不断地向任务列表jobs中添加Job，
 *                  而每个工作者线程会不断地从jobs上取出一个Job进行执行,当jobs为空时,工作者线程进入等待状态
 *
 *      线程池的本质就是使用了一个线程安全的工作队列连接工作者线程和客户端线程，
 *          客户端线程将任务放入工作队列后便返回，而工作者线程则不断地从工作队列上取出工作并执行。
 *          当工作队列为空时，所有的工作者线程均等待在工作队列上，当有客户端提交了一个任务之后会通知任意一个工作者线程，
 *          随着大量的任务被提交，更多的工作者线程会被唤醒。
 */
public class DefaultThreadPool<Job extends Runnable> implements ThreadPool<Job> {

    //线程池最大限制数
    private static final int MAX_WORKER_NUMBERS = 10;

    //线程池默认的数量
    private static final int DEFAULT_WORKER_NUMBERS = 5;

    //线程池最小的数量
    private static final int MIN_WORKER_NUMBERS = 1;

    //工作列表，将会向这里插入工作
    private final LinkedList<Job> jobs = new LinkedList<Job>();

    //工作者列表
    private final List<Worker> workers = Collections.synchronizedList(new ArrayList<Worker>());

    //工作者线程的数量
    private int workerNum = DEFAULT_WORKER_NUMBERS;

    //线程编号生成
    private AtomicLong threadNum = new AtomicLong();

    public DefaultThreadPool(){
        initializeWorkers(DEFAULT_WORKER_NUMBERS);
    }
    public DefaultThreadPool(int num){
        workerNum = num > MAX_WORKER_NUMBERS ? MAX_WORKER_NUMBERS : num < MIN_WORKER_NUMBERS ? MIN_WORKER_NUMBERS : num;
        initializeWorkers(workerNum);
    }

    /**
     * 初始化线程工作者
     * @param num
     */
    private void initializeWorkers(int num) {
        for (int i = 0; i < num; i++) {
            Worker worker = new Worker();
            workers.add(worker);
            Thread thread = new Thread(worker,"ThreadPool-Worker-" + threadNum.incrementAndGet());
            thread.start();
        }
    }

    /**
     * 工作者，负责消费任务
     */
    class Worker implements Runnable{
        //是否工作
        private volatile boolean running = true;
        @Override
        public void run() {
            while(running){
                Job job = null;
                synchronized (jobs){
                    //如果工作列表是空的，那就wait
                    while(jobs.isEmpty()){
                        try {
                            jobs.wait();
                        } catch (InterruptedException e) {
                            //感知到外部对WorkerThread的中断操作，返回
                            Thread.currentThread().interrupt();
                            return ;
                        }
                    }
                    //取出一个job
                    job = jobs.removeFirst();
                }
                if(job != null){
                    try{
                        job.run();
                    }catch (Exception e){
                        //忽略job执行中的Exception
                    }
                }
            }
        }

        public void shutdown(){
            running = false;
        }
    }

    @Override
    public void execute(Job job) {
        if(job != null){
            synchronized (jobs){
                //添加一个工作，然后进行通知
                jobs.add(job);
                jobs.notify();
            }
        }
    }

    @Override
    public void shutdown() {
        for(Worker worker : workers){
            worker.shutdown();
        }
    }

    @Override
    public void addWorkers(int num) {
        synchronized (jobs){
            //限制新增的Worker数量不能超过最大值
            if(num + this.workerNum > MAX_WORKER_NUMBERS){
                num = MAX_WORKER_NUMBERS;
            }
            initializeWorkers(num);
            this.workerNum += num;
        }
    }

    @Override
    public void removeWorker(int num) {
        synchronized (jobs){
            if(num >= this.workerNum){
                throw new IllegalArgumentException("beyond workNum");
            }
            //按照给定的数量停止Worker
            int count = 0;
            while(count < num){
                Worker worker = workers.get(count);
                if(workers.remove(worker)){
                    count++;
                }
            }
            this.workerNum -= count;
        }
    }

    @Override
    public int getJobSize() {
        return jobs.size();
    }


}
