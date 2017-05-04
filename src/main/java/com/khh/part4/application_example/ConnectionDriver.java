package com.khh.part4.application_example;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

/**
 * Created by 951087952@qq.com on 2017/5/4.
 *  由于java.sql.Connection是一个接口，最终实现是由数据库驱动提供来实现的，考虑到这里是一个示例
 *  ，通过动态代理构造一个Connection
 */
public class ConnectionDriver {

    static class ConnectionHandler implements InvocationHandler{

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if(method.getName().equals("commit")){
                Thread.sleep(100);
            }
            return null;
        }
    }

    //创建一个Connection的代理,在commit时休眠100毫秒
    public static final Connection createConnection(){

        return (Connection)Proxy.newProxyInstance(ConnectionHandler.class.getClassLoader(),
                new Class<?>[]{Connection.class},new ConnectionHandler());
    }
}
