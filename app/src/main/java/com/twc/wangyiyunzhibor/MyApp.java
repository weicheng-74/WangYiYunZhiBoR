package com.twc.wangyiyunzhibor;

import android.app.Application;

import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.util.NIMUtil;
import com.twc.wangyiyunzhibor.mymessage.CustomAttachParser;
import com.twc.wangyiyunzhibor.mymessage.GuessAttachment;
import com.twc.wangyiyunzhibor.mymessage.guest.MsgViewHolderGuess;
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
            // 初始化
            NimUIKit.init(this);
//
//            // 可选定制项
//            // 注册定位信息提供者类（可选）,如果需要发送地理位置消息，必须提供。
//            // demo中使用高德地图实现了该提供者，开发者可以根据自身需求，选用高德，百度，google等任意第三方地图和定位SDK。
//            NimUIKit.setLocationProvider(new NimDemoLocationProvider());
//
//            // 会话窗口的定制: 示例代码可详见demo源码中的SessionHelper类。
//            // 1.注册自定义消息附件解析器（可选）
//            // 2.注册各种扩展消息类型的显示ViewHolder（可选）
//            // 3.设置会话中点击事件响应处理（一般需要）
//            SessionHelper.init();
//
//            // 通讯录列表定制：示例代码可详见demo源码中的ContactHelper类。
//            // 1.定制通讯录列表中点击事响应处理（一般需要，UIKit 提供默认实现为点击进入聊天界面)
//            ContactHelper.init();
//
//            // 在线状态定制初始化。
//            NimUIKit.setOnlineStateContentProvider(new DemoOnlineStateContentProvider());
            NIMClient.getService(MsgService.class).registerCustomAttachmentParser(new CustomAttachParser());
            NimUIKit.registerMsgItemViewHolder(GuessAttachment.class, MsgViewHolderGuess.class);
        }
    }

    private LoginInfo getLoginInfo() {
        return new LoginInfo("777777", "777777");
    }

}
