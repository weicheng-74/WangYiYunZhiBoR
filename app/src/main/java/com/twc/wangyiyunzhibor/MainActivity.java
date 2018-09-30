package com.twc.wangyiyunzhibor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.business.session.activity.P2PMessageActivity;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 账号1 ：777777
 * token 777777
 * <p>
 * 账号2 ：888888
 * token 888888
 */
public class MainActivity extends UI {

    @BindView(R.id.button)
    Button button;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.button3)
    Button button3;
    @BindView(R.id.textmsg)
    TextView textmsg;
    @BindView(R.id.button4)
    Button button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NIMClient.getService(AuthService.class).login(new LoginInfo("777777", "777777"))
                        .setCallback(new RequestCallback() {
                            @Override
                            public void onSuccess(Object param) {
                                Log.e("twc", param.toString());
                                Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailed(int code) {
                                Log.e("twc", code + "---");
                            }

                            @Override
                            public void onException(Throwable exception) {
                                Log.e("twc", exception.toString());
                            }
                        });

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NIMClient.getService(AuthService.class).logout();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 该帐号为示例，请先注册
                String account = "888888";
                // 以单聊类型为例
                SessionTypeEnum sessionType = SessionTypeEnum.P2P;
                String text = "this is an example";
                // 创建一个文本消息
                IMMessage textMessage = MessageBuilder.createTextMessage(account, sessionType, text);
                // 发送给对方  如果是发送失败后重发，标记为true，否则填false
                NIMClient.getService(MsgService.class).sendMessage(textMessage, false);

            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NimUIKit.loginSuccess("777777");
                // 打开单聊界面
                NimUIKit.startP2PSession(MainActivity.this, "888888",null);
//                P2PMessageActivity.start(MainActivity.this, "888888", "888888", "888888");
            }
        });
    }
}
