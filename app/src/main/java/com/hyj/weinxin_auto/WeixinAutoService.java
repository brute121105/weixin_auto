package com.hyj.weinxin_auto;

import android.accessibilityservice.AccessibilityService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.alibaba.fastjson.JSON;
import com.hyj.weinxin_auto.common.Constants;
import com.hyj.weinxin_auto.service.WxAddFriendService;
import com.hyj.weinxin_auto.service.WxChatService;
import com.hyj.weinxin_auto.service.WxLoginService;
import com.hyj.weinxin_auto.service.WxSendFrMsgService;
import com.hyj.weinxin_auto.util.AutoUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeixinAutoService extends AccessibilityService {
    Map<String,String> record = new HashMap<String,String>();
    WeixinAutoHandler handler =null;
    public WeixinAutoService() {
        //初始化标志监听状态
        handler = WeixinAutoHandler.getInstance();
        AutoUtil.recordAndLog(record,Constants.CHAT_LISTENING);
    }
    WxLoginService wLogin = new WxLoginService();
    WxSendFrMsgService wSendFrMsg = new WxSendFrMsgService();
    WxChatService chat = new WxChatService();
    WxAddFriendService addFr = new WxAddFriendService();
    @Override
        public void onAccessibilityEvent(AccessibilityEvent event) {
        System.out.println("----->eventType:"+event.getEventType());
        AccessibilityNodeInfo nodeInfo = this.getRootInActiveWindow();
        addFr.autoAddFriend(event,nodeInfo,WeixinAutoService.this,record);
        if(handler.IS_AUTO_LOGIN)
            wLogin.autoLogin(event,nodeInfo);
        if(handler.IS_AUTO_CHAT)
            chat.autoChat(event,nodeInfo,WeixinAutoService.this,record);
        if(handler.IS_AUTO_SENDFRMSG)
            wSendFrMsg.autoSendFrMsg(event,nodeInfo,WeixinAutoService.this,record);
    }
    @Override
    public void onInterrupt() {

    }
}
