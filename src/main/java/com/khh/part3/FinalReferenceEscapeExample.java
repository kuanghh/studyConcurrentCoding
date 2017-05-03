package com.khh.part3;

/**
 * Created by 951087952@qq.com on 2017/5/3.
 */
public class FinalReferenceEscapeExample {

    final int i;
    static FinalReferenceEscapeExample obj;
    public FinalReferenceEscapeExample(){
        i = 1;        // 写final域
        obj = this;   //this引用在此“逸出”
    }
    public static void writer(){
        new FinalReferenceEscapeExample();
    }
    public static void reader(){
        if(obj != null){
            int temp = obj.i;
        }
    }
}
