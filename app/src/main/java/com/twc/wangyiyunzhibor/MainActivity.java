package com.twc.wangyiyunzhibor;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.chatroom.ChatRoomService;
import com.netease.nimlib.sdk.chatroom.model.EnterChatRoomData;
import com.netease.nimlib.sdk.chatroom.model.EnterChatRoomResultData;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.team.TeamService;
import com.netease.nimlib.sdk.team.constant.TeamFieldEnum;
import com.netease.nimlib.sdk.team.constant.TeamTypeEnum;
import com.netease.nimlib.sdk.team.constant.VerifyTypeEnum;
import com.netease.nimlib.sdk.team.model.CreateTeamResult;
import com.twc.wangyiyunzhibor.bean.MsgEvent;
import com.twc.wangyiyunzhibor.mymessage.GuessAttachment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 网易云账号。
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
    @BindView(R.id.button5)
    Button button5;
    @BindView(R.id.button6)
    Button button6;
    @BindView(R.id.button7)
    Button button7;
    @BindView(R.id.button8)
    Button button8;
    @BindView(R.id.button9)
    Button button9;
    private Context context;

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void MsgEvent(MsgEvent event) {
        if (event != null) {
            textmsg.setText("实时情况：" + event.getMsg());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    String groupId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        context = this;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NIMClient.getService(AuthService.class).login(new LoginInfo("777777", "777777"))
                        .setCallback(new RequestCallback() {
                            @Override
                            public void onSuccess(Object param) {
                                Log.e("twc", param.toString());
                                NimUIKit.loginSuccess("777777");
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
                // 打开单聊界面
                NimUIKit.startP2PSession(MainActivity.this, "888888", null);
//                P2PMessageActivity.start(MainActivity.this, "888888", "888888", "888888");
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                fields - 群组预设资料, key为数据字段，value对对应的值，该值类型必须和field中定义的fieldType一致。
//                type - 要创建的群组类型
//                postscript - 邀请入群的附言。如果是创建临时群，该参数无效
//                members - 邀请加入的成员帐号列表
                List<String> sd = new ArrayList<String>();
                sd.add("777777");
                sd.add("888888");
                HashMap<TeamFieldEnum, Serializable> fields = new HashMap<TeamFieldEnum, Serializable>();
                fields.put(TeamFieldEnum.Name, "测试群");
//                VerifyType:申请入群的验证方式
//                InviteMode 群邀请模式：谁可以邀请他人入群
                fields.put(TeamFieldEnum.VerifyType, VerifyTypeEnum.Free);
                NIMClient.getService(TeamService.class)
                        //  TeamTypeEnum  Normal(0), 普通群
                        //    Advanced(1);高级群
                        .createTeam(fields, TeamTypeEnum.Normal, "", sd)
                        .setCallback(new RequestCallback<CreateTeamResult>() {
                            @Override
                            public void onSuccess(CreateTeamResult createTeamResult) {
                                Log.e("result", "成功创建群组" + createTeamResult.toString());
                                groupId = createTeamResult.getTeam().getId();
                                Log.e("result", "成功创建群组id" + createTeamResult.getTeam().getId());
                            }

                            @Override
                            public void onFailed(int i) {
                                Log.e("result", i + "");
                            }

                            @Override
                            public void onException(Throwable throwable) {
                                Log.e("result", throwable + "");
                            }
                        });


            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 打开群聊界面
                NimUIKit.startTeamSession(context, groupId);
            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // roomId 表示聊天室ID
                EnterChatRoomData data = new EnterChatRoomData("ooo111");
// 以登录一次不重试为例
                NIMClient.getService(ChatRoomService.class).enterChatRoomEx(data, 1).setCallback(new RequestCallback<EnterChatRoomResultData>() {
                    @Override
                    public void onSuccess(EnterChatRoomResultData result) {
                        Log.e("result", result + "登陆聊天室成功");
                        // 登录成功
                    }

                    @Override
                    public void onFailed(int code) {
                        Log.e("result", code + "登陆聊天室失败");
                        // 登录失败
                    }

                    @Override
                    public void onException(Throwable exception) {
                        // 错误
                        Log.e("result", exception + "登陆聊天室失败");
                    }
                });
            }
        });
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GuessAttachment attachment = new GuessAttachment();
                IMMessage message = MessageBuilder.createCustomMessage("888888", SessionTypeEnum.P2P,
                        attachment.getValue().getDesc(), attachment);
                NIMClient.getService(MsgService.class).sendMessage(message, false);
            }
        });
    }
}
