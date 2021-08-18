package com.example.wwh.otto_test;

import com.squareup.otto.Bus;

/**
 * Created by Administrator on 2021/08/17
 * 单例模式
 */
public class OttoBus extends Bus {
    private volatile static OttoBus mBus;

    private OttoBus (){
    }

    public static OttoBus getInstance() {
        if (null == mBus) {
            synchronized (OttoBus.class){
             if(null == mBus){
                 mBus = new OttoBus();
             }
            }
        }

        return mBus;
    }
}
