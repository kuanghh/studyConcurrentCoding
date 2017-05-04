package com.khh.part4.test_pipe;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * Created by 951087952@qq.com on 2017/5/4.
 * 测试 管道输入/输出流 它主要用于线程之间的数据传输，而传输的媒介为内存
 */
public class Piped {

    static class Print implements  Runnable{
        private PipedReader in;
        public Print(PipedReader in){
            this.in = in;
        }
        @Override
        public void run() {
            int receive = 0;
            try {
                while((receive = in.read())!= -1){
                    System.out.print((char)receive);
                }
            }catch (IOException e){

            }
        }
    }

    public static void main(String[] args) throws IOException {
        PipedWriter out = new PipedWriter();
        PipedReader in = new PipedReader();

        //这里必须连接输出流和输入流，不然使用时候会抛出IOException
        out.connect(in);
        Thread printThread = new Thread(new Print(in),"PrintThread");
        printThread.start();
        int receive = 0;
        try {
            while((receive = System.in.read()) != -1){
                out.write(receive);
            }
        }finally {
            out.close();
        }


    }
}
