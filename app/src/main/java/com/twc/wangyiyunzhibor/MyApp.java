package com.twc.wangyiyunzhibor;

import android.app.Application;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.util.NIMUtil;
import com.twc.wangyiyunzhibor.register.IMListener;

/**
 * 账号1 ：777777
 * token 777777
 * <p>
 * 账号2 ：888888
 * token 888888
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NIMClient.init(this, null, null);

        if (NIMUtil.isMainProcess(this)) {
            //判断是否在主线程。
            IMListener.getInstance().init(this);
        }
    }

    private LoginInfo getLoginInfo() {
        return new LoginInfo("777777", "777777");
    }

}
