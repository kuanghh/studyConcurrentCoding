package com.khh.part6._fork_join;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Created by 951087952@qq.com on 2017/5/10.
 *  用Fork/Join框架计算 1+2+3+4+5+6 的结果
 *
 *  因为是有结果的任务，所以必须继承RecursiveTask，否则继承RecursiveAction
 */
public class CountTask extends RecursiveTask<Integer>{

    /**
     * 因为希望每个子任务最多执行两个数相加，那我们设置分割的阈值是2
     */
    private static final int THRESHOLD = 2;//阈值
    private int start ;
    private int end;
    public CountTask(int start,int end){
        this.start = start;
        this.end = end;
    }


    @Override
    protected Integer compute() {
        int sum = 0;
        boolean canCompute = end - start <= THRESHOLD;
        if(canCompute){
            for(int i = start; i <= end; i++){
                sum += i;
            }
        }else{
            //如果任务大于阈值，就分裂成两个子任务计算
            int middle = (start + end) / 2;
            CountTask leftTask = new CountTask(start,middle);
            CountTask rightTask = new CountTask(middle+1,end);
            //执行子任务
            leftTask.fork();
            rightTask.fork();
            //等待子任务执行完，并得到其结果
            Integer leftResult = leftTask.join();
            Integer rightResult = rightTask.join();
            sum = leftResult + rightResult;
        }
        return sum;
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        //生成一个计算任务，负责计算1+2+3+4+5+6
        CountTask countTask = new CountTask(1,6);
        //执行一个任务
        ForkJoinTask<Integer> result = forkJoinPool.submit(countTask);
        try {
            System.out.println(result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
