package com.khh.part5.fair_and_unfair;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Created by 951087952@qq.com on 2017/5/9.
 * 测试公平锁和非公平锁
 *
 * 公平锁输出：
 *   Lock by [1].Waiting By[0, 4, 2, 3]
     Lock by [0].Waiting By[4, 2, 3, 1]
     Lock by [4].Waiting By[2, 3, 1, 0]
     Lock by [2].Waiting By[3, 1, 0, 4]
     Lock by [3].Waiting By[1, 0, 4, 2]
     Lock by [1].Waiting By[0, 4, 2, 3]
     Lock by [0].Waiting By[4, 2, 3]
     Lock by [4].Waiting By[2, 3]
     Lock by [2].Waiting By[3]
     Lock by [3].Waiting By[]

    非公平锁输出:
     Lock by [0].Waiting By[1, 2, 4, 3]
     Lock by [0].Waiting By[1, 2, 4, 3]
     Lock by [1].Waiting By[2, 4, 3]
     Lock by [1].Waiting By[2, 4, 3]
     Lock by [2].Waiting By[4, 3]
     Lock by [2].Waiting By[4, 3]
     Lock by [4].Waiting By[3]
     Lock by [4].Waiting By[3]
     Lock by [3].Waiting By[]
     Lock by [3].Waiting By[]

    证明了，使用非公平锁的前提下，刚释放锁的线程再次获取同步状态的几率会非常大，使得其它线程只能在同步队列中等待
            使用公平锁的前提下，等待越久的线程越是能得到优先满足
 */
public class FairAndUnfairTest {
    private static Lock fairLock = new ReentrantLock2(true);
    private static Lock unFairLock = new ReentrantLock2(false);


    public static void fair(){
        testLock(fairLock);
    }


    public static void unFair(){
        testLock(unFairLock);
    }

    public static void main(String[] args) {
//        fair();
        testLock(unFairLock);
    }

    private static void testLock(Lock lock){
        for (int i = 0; i < 5; i++) {
            Job job = new Job(lock){
                @Override
                public String toString() {
                    return getName();
                }
            };
            job.setName(String.valueOf(i));
            job.start();
        }
    }

    private static class Job extends Thread{
        private Lock lock;
        public Job(Lock lock){
            this.lock = lock;
        }
        public void run(){
            if(this.lock instanceof ReentrantLock2){
                ReentrantLock2 rlock = (ReentrantLock2) this.lock;
                for (int i = 0; i < 2; i++) {
                    rlock.lock();
                    try {
                        Thread.sleep(2000);
                        List<Thread> list = (List<Thread>) rlock.getQuueThreads();
                        System.out.println("Lock by [" + Thread.currentThread().getName() + "].Waiting By" + list);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
            }
        }
    }

    private static class ReentrantLock2 extends ReentrantLock{
        public ReentrantLock2(boolean fair){
            super(fair);
        }
        public Collection<Thread> getQuueThreads(){
            List<Thread> list = new ArrayList<>(super.getQueuedThreads());
            Collections.reverse(list);
            return list;
        }
    }
}
