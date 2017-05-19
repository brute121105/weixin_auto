package com.hyj.weinxin_auto.service;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.alibaba.fastjson.JSON;
import com.hyj.weinxin_auto.GlobalApplication;
import com.hyj.weinxin_auto.WeixinAutoService;
import com.hyj.weinxin_auto.common.Constants;
import com.hyj.weinxin_auto.util.AutoUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by asus on 2017/5/13.
 *TYPE_WINDOW_CONTENT_CHANGED收到消息触发（在微信“微信”界面）
 * TYPE_NOTIFICATION_STATE_CHANGED收到消息触发（1在微信“微信”外界面、关闭屏蔽、亮屏不在微信界面）
 * 1、消息列表获取信息头像
 * 2、获取消息条数、进入聊天窗口
 * 3、获取当前聊天窗口信息
 * 4、返回消息列表
 * 5、打开转发聊天窗口
 * 6、填充第3步已获取消息到输入框
 * 7、发送
 */

public class WxAddFriendService {
    int wxinIndex;
    public WxAddFriendService() {
        wxinIndex = 0;
    }
    public void autoAddFriend(AccessibilityEvent event, AccessibilityNodeInfo node, WeixinAutoService context, Map<String,String> record){
        if(node==null) return;
        if(getWxids().length==wxinIndex) return;
        int eventType = event.getEventType();
        AccessibilityNodeInfo homePageNode = AutoUtil.fineNodeByIdAndText(node,"android:id/text1","微信");
       /*1、点击[通讯录]页签 中[新的朋友]
        * 步不需要先切换到[通讯录]页签，可以直接点击
        */
        if(AutoUtil.checkAction(record,Constants.CHAT_LISTENING)&&homePageNode!=null){
            AccessibilityNodeInfo newFr = AutoUtil.findNodeInfosByText(context.getRootInActiveWindow(),"新的朋友");
            AutoUtil.performClick(newFr,record,Constants.AddFR_ACTION_01);
        }
        //2、点击[微信号/QQ号/手机号]输入框
        if(AutoUtil.checkAction(record,Constants.AddFR_ACTION_01)){
            AccessibilityNodeInfo clickBtn = AutoUtil.findNodeInfosById(node,"com.tencent.mm:id/awd");
            AutoUtil.performClick(clickBtn,record,Constants.AddFR_ACTION_02);
        }
        //3、输入微信号码
        if(AutoUtil.checkAction(record,Constants.AddFR_ACTION_02)||AutoUtil.checkAction(record,Constants.AddFR_ACTION_08)){
            AccessibilityNodeInfo queryWxIdNode = AutoUtil.findNodeInfosById(node,"com.tencent.mm:id/gr");
            AutoUtil.performSetText(queryWxIdNode,getWxids()[wxinIndex],record,Constants.AddFR_ACTION_03);
            if(queryWxIdNode!=null) wxinIndex = wxinIndex+1;
        }
        //4、点击搜索
        if(AutoUtil.checkAction(record,Constants.AddFR_ACTION_03)){
            AccessibilityNodeInfo queryNode = AutoUtil.findNodeInfosById(node,"com.tencent.mm:id/ho");
            AutoUtil.performClick(queryNode,record,Constants.AddFR_ACTION_04);
        }
        //5、添加到通讯录（两种情况 用户不存在、搜索成果）
        if(AutoUtil.checkAction(record,Constants.AddFR_ACTION_04)){
            AutoUtil.sleep(5000);
            AccessibilityNodeInfo queryResult1 = AutoUtil.findNodeInfosById(context.getRootInActiveWindow(),"com.tencent.mm:id/ayf");
            AccessibilityNodeInfo queryResult2 = AutoUtil.findNodeInfosByText(context.getRootInActiveWindow(),"添加到通讯录");
            if(queryResult1!=null){
                AccessibilityNodeInfo queryWxIdNode = AutoUtil.findNodeInfosById(node,"com.tencent.mm:id/gr");
                AutoUtil.performSetText(queryWxIdNode,getWxids()[wxinIndex],record,Constants.AddFR_ACTION_03);
                if(queryWxIdNode!=null) wxinIndex = wxinIndex+1;
            }else if(queryResult2!=null){
                AutoUtil.performClick(queryResult2,record,Constants.AddFR_ACTION_05);
            }
        }
        //6、发送
        if(AutoUtil.checkAction(record,Constants.AddFR_ACTION_05)){
            AutoUtil.sleep(2000);
            AccessibilityNodeInfo sendBtn = AutoUtil.findNodeInfosById(context.getRootInActiveWindow(),"com.tencent.mm:id/gd");
            AutoUtil.performClick(sendBtn,record,Constants.AddFR_ACTION_06);
        }
        //7、返回上一步
        if(AutoUtil.checkAction(record,Constants.AddFR_ACTION_06)){
            AutoUtil.sleep(2000);
            AutoUtil.performBack(context,record,Constants.AddFR_ACTION_07);
        }
        //修改状态，回到微信输入搜索框
        if(AutoUtil.checkAction(record,Constants.AddFR_ACTION_07)){
            AutoUtil.recordAndLog(record,Constants.AddFR_ACTION_08);
        }
    }
    private String[] getWxids(){
        SharedPreferences sharedPreferences = GlobalApplication.getContext().getSharedPreferences("wxids",MODE_PRIVATE);
        String wxids = sharedPreferences.getString("wxid","");
        String[] wxidStr = wxids.split("\n");

        return wxidStr;
    }
}
