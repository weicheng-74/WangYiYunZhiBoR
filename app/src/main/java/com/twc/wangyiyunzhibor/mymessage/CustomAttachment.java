package com.twc.wangyiyunzhibor.mymessage;

import com.alibaba.fastjson.JSONObject;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;

public abstract class CustomAttachment implements MsgAttachment {
    // 自定义消息附件的类型，根据该字段区分不同的自定义消息
    protected int type;

    CustomAttachment(int type) {
        this.type = type;
    }

    // 解析附件内容。
    public void fromJson(JSONObject data) {
        if (data != null) {
            parseData(data);
        }
    }

    @Override
    public String toJson(boolean b) {
        return CustomAttachParser.packData(type, packData());
    }

    public int getType() {
        return type;
    }

    // 子类的解析和封装接口。
    protected abstract void parseData(JSONObject data);

    protected abstract JSONObject packData();
}
