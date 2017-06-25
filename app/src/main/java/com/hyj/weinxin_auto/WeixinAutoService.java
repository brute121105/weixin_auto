package com.hyj.weinxin_auto;

import android.accessibilityservice.AccessibilityService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.alibaba.fastjson.JSON;
import com.hyj.weinxin_auto.common.Constants;
import com.hyj.weinxin_auto.service.WxAddFriendService;
import com.hyj.weinxin_auto.service.WxAutoChatService;
import com.hyj.weinxin_auto.service.WxChatService;
import com.hyj.weinxin_auto.service.WxFetchFrDataService;
import com.hyj.weinxin_auto.service.WxLoginService;
import com.hyj.weinxin_auto.service.WxSendFrMsgService;
import com.hyj.weinxin_auto.util.AutoUtil;
import com.hyj.weinxin_auto.util.LogUtil;

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
    WxAutoChatService autoChatService = new WxAutoChatService();
    WxAddFriendService addFr = new WxAddFriendService();
    WxFetchFrDataService fetchFrData = new WxFetchFrDataService();
    @Override
        public void onAccessibilityEvent(AccessibilityEvent event) {
        System.out.println("----->eventType:"+event.getEventType());
        AccessibilityNodeInfo nodeInfo = this.getRootInActiveWindow();
        if(nodeInfo==null) {
            LogUtil.d("nodeInfo2",nodeInfo+"");
            return;
        }
        if(handler.IS_AUTO_ADDFR)
            addFr.autoAddFriend(event,nodeInfo,WeixinAutoService.this,record);
        if(handler.IS_AUTO_FETCHDATA)
            fetchFrData.autoFetchFrData(event,nodeInfo,WeixinAutoService.this,record);
        if(handler.IS_AUTO_LOGIN)
            wLogin.autoLogin(event,nodeInfo,WeixinAutoService.this,record);
        if(handler.IS_AUTO_CHAT)
            //chat.autoChat(event,nodeInfo,WeixinAutoService.this,record);
            autoChatService.autoChat(event,nodeInfo,WeixinAutoService.this,record);
        if(handler.IS_AUTO_SENDFRMSG)
            wSendFrMsg.autoSendFrMsg(event,nodeInfo,WeixinAutoService.this,record);
    }
    @Override
    public void onInterrupt() {

    }
}
