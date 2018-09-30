package com.twc.wangyiyunzhibor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.netease.nim.uikit.business.session.activity.P2PMessageActivity;

public class MyMessageActivity extends P2PMessageActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message);
    }
}
