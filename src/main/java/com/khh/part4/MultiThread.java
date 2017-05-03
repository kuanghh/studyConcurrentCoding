package com.khh.part4;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * Created by 951087952@qq.com on 2017/5/3.
 *
 */
public class MultiThread {
    public static void main(String[] args) {
        //获取java线程管理MXBean
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        //不需要获取同步的monitor和synchronizer信息，仅获取线程和线程堆栈信息
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        //遍历线程信息，仅打印线程Id和线程名称信息
        for (ThreadInfo threadInfo:threadInfos){
            System.out.println("[" + threadInfo.getThreadId() + "]" + threadInfo.getThreadName());
        }
    }

    /**
     * 输出：
     *   [10]Monitor Ctrl-Break
         [5]Attach Listener
         [4]Signal Dispatcher   //分发处理发送给JVM信号的线程
         [3]Finalizer           //调用对象finalize方法的线程
         [2]Reference Handler   //清楚Reference的线程
         [1]main               //main线程
     */
}
