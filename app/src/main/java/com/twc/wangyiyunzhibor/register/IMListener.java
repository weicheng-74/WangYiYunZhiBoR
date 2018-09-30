package com.twc.wangyiyunzhibor.register;

import android.content.Context;
import android.util.EventLog;
import android.util.Log;
import android.widget.Toast;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.twc.wangyiyunzhibor.MyApp;
import com.twc.wangyiyunzhibor.bean.MsgEvent;

import org.greenrobot.eventbus.EventBus;

public class IMListener {
    private static IMListener instance = null;

    private IMListener() {

    }

    public static IMListener getInstance() {
        if (instance == null) {
            synchronized (IMListener.class) {
                if (instance == null) {
                    instance = new IMListener();
                }
            }
        }
        return instance;
    }

    private Context context;

    public void init(MyApp myApp) {
        this.context = myApp;
        /**
         * 注册/注销在线状态变化观察者。<br>
         * 注册后，Observer的onEvent方法会被立即调用一次，告知观察者当前状态。
         *
         * @param observer 观察者, 参数为当前状态
         * @param register true为注册，false为注销
         */
        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(registOrUnRegist, true);
        NIMClient.getService(MsgServiceObserve.class).observeMsgStatus(msgObserver, true);
    }

    private Observer<IMMessage> msgObserver = new Observer<IMMessage>() {
        @Override
        public void onEvent(IMMessage imMessage) {

        }
    };
    Observer<StatusCode> registOrUnRegist = new Observer<StatusCode>() {
        @Override
        public void onEvent(StatusCode statusCode) {
            switch (statusCode) {
                case LOGINED:
                    EventBus.getDefault().post(new MsgEvent("已经登录成功"));
//                    Toast.makeText(context, "已经登录成功", Toast.LENGTH_SHORT).show();
                    break;
                case UNLOGIN:
                    EventBus.getDefault().post(new MsgEvent("未登录或者登录失败"));
//                    Toast.makeText(context, "未登录/登录失败", Toast.LENGTH_SHORT).show();
                    break;
            }
            Log.e("twc", statusCode.toString());
        }
    };

    public void onDestory() {
        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(registOrUnRegist, false);
    }

}
