package com.example.wwh.otto_test;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.app.Activity;

/**
 * Created by Administrator on 2021/8/16.
 */

public class OKHttpTest extends Activity {
    final private String TAG = OKHttpTest.class.getName();
    private com.example.wwh.otto_test.OttoBus mBus;

    public OKHttpTest(){
        mBus = OttoBus.getInstance();
        mBus.register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBus.unregister(this);
    }

    public void test() {
        //1.创建OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)//设置连接超时时间//单位是秒
                .writeTimeout(10, TimeUnit.SECONDS)//设置写超时时间
                .readTimeout(20, TimeUnit.SECONDS)//设置读取超时时间
                .build();

        //2.创建Request对象；本次采用get作为样例，如果想用post或其他消息，可以自行修改
        Request request = new Request.Builder()
                .url("https://cn.bing.com/") //请求的url
                .get()
                .build();

        //3.创建一个call对象,参数就是Request请求对象
        Call call = okHttpClient.newCall(request);

        //4.请求加入调度，重写回调方法
        call.enqueue(new Callback() {
            //请求失败执行的方法
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e(TAG, "getTaskTargetList Fail: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response == null) {
                    return;
                }

                final String result = response.body().string();
                Log.e(TAG, "respnse:\n" + result);


                runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           OttoBus.getInstance().post(new BusData(result));
                       }
               });
            }
        });
    }
}
