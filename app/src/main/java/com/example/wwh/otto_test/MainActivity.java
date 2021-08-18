package com.example.wwh.otto_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class MainActivity extends AppCompatActivity {
    private Button mBtnHttpPost;
    private TextView mTvMessage;
    private Bus mBus;
    private OKHttpTest mOKHttpTest = new OKHttpTest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTvMessage = (TextView) this.findViewById(R.id.tv_message);
        mBtnHttpPost = (Button) this.findViewById(R.id.bt_http_post);
        mBtnHttpPost.setText("Http异步测试");

        mBtnHttpPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOKHttpTest.test();
            }
        });

        mBus = OttoBus.getInstance();
        mBus.register(this);
    }

    @Subscribe
    public void setContent(BusData data) {
        Log.i("http async","Subscribe");
        mTvMessage.setText(data.getMessage().substring(0, 10));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBus.unregister(this);
    }
}
