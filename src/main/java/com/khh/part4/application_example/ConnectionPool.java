package com.khh.part4.application_example;



import java.sql.Connection;
import java.util.LinkedList;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by 951087952@qq.com on 2017/5/4.
 * 模拟数据库连接池
 */
public class ConnectionPool {

    private LinkedList<Connection> pool = new LinkedList<>();

    /**
     * 构造线程池对象时候，要指定线程池中线程的初始化数量 ,如果数量小于等于0，则抛出异常
     * @param initialSize
     */
    public ConnectionPool(int initialSize){
        if(initialSize <= 0){
            throw new RuntimeException();
        }
        for (int i = 0; i < initialSize; i++) {
            pool.add(ConnectionDriver.createConnection());
        }
    }

    /**
     * 归还连接
     * @param connection
     */
    public void releaseConnection(Connection connection){
        if(connection != null){
            synchronized (pool){
                //连接释放后需要进行通知，这样其他消费者能够感知到连接池中已归还了一个连接
                pool.addLast(connection);
                pool.notifyAll();
            }
        }
    }

    /**
     * 在mills内无法获取连接，将返回null
     * @param mills
     * @return
     * @throws InterruptedException
     */
    public Connection fetchConnection(long mills) throws InterruptedException{
        synchronized (pool){
            //完全超时
            if(mills <= 0){
                while(pool.isEmpty()){
                    pool.wait();
                }
                return pool.removeFirst();
            }else{
                long futrue = System.currentTimeMillis() + mills;
                long remaining = mills;
                /**
                    书上的代码如下，个人分析：
                        当要跳出while循环的情况有3中
                        1）池空，已超时
                        2）池不空，未超时
                        3）池不空，已超时
                        如果是跳出while循环的情况是1,返回的是null
                        如果是跳出while循环的情况是2，返回的是Connection对象
                        如果是跳出while循环的情况是3，依然可以返回Connection对象，而不是null，因此改进一下代码
                 */

                /**书上的代码*/
                /*while(pool.isEmpty() && remaining > 0){
                    pool.wait();
                    remaining = futrue - System.currentTimeMillis();
                }
                Connection result = null;
                if(!pool.isEmpty()){
                    result = pool.removeFirst();
                }
                return result;*/

                /**个人修改后的代码*/
                while(pool.isEmpty() && remaining > 0){
                    pool.wait();
                    remaining = futrue - System.currentTimeMillis();
                }
                //超时了
                if(remaining <= 0){
                    return null;
                }else{//根据上面的分析，若未超时，池肯定不空
                    return pool.removeFirst();
                }
            }
        }

    }
}
