package com.hyj.weinxin_auto.service;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.alibaba.fastjson.JSON;
import com.hyj.weinxin_auto.WeixinAutoService;
import com.hyj.weinxin_auto.common.Constants;
import com.hyj.weinxin_auto.util.AutoUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
    //String recordAction="";
    //Map<String,String> record = new HashMap<String,String>();
    public void autoAddFriend(AccessibilityEvent event, AccessibilityNodeInfo node,WeixinAutoService context,Map<String,String> record){
        //if(node==null||!record.get("recordAction").equals(Constants.CHAT_LISTENING)) return;
        int eventType = event.getEventType();
        AccessibilityNodeInfo homePageNode = AutoUtil.fineNodeByIdAndText(node,"android:id/text1","微信");
        if(homePageNode!=null){
             /*1、点击[通讯录]页签 中[新的朋友]
             * 步不需要先切换到[通讯录]页签，可以直接点击
              */
            AccessibilityNodeInfo newFr = AutoUtil.findNodeInfosByText(context.getRootInActiveWindow(),"新的朋友");
            AutoUtil.performClick(newFr,record,"点击[通讯录]页签中[新的朋友]");
            //2、点击[微信号/QQ号/手机号]输入框
            AccessibilityNodeInfo clickBtn = AutoUtil.fineNodeByIdAndTextCheck(node,"com.tencent.mm:id/awd",null,context,record,"初始状态");
            AutoUtil.performClick(clickBtn,record,"点击[微信号/QQ号/手机号]输入框");
            //3、输入微信号码
            AccessibilityNodeInfo queryWxIdNode = AutoUtil.fineNodeByIdAndTextCheck(node,"com.tencent.mm:id/gr",null,context,record,"初始状态");
            AutoUtil.performSetText(queryWxIdNode,"hy2222",record,"输入微信号码");
            //4、点击搜索
            AccessibilityNodeInfo queryNode = AutoUtil.fineNodeByIdAndTextCheck(node,"com.tencent.mm:id/ho",null,context,record,"初始状态");
            AutoUtil.performClick(queryNode,record,"点击搜索");
            //5、添加到通讯录（两种情况 用户不存在、搜索成果）
            AutoUtil.sleep(5000);
            AccessibilityNodeInfo queryResult1 = AutoUtil.findNodeInfosById(context.getRootInActiveWindow(),"com.tencent.mm:id/ayf");
            AccessibilityNodeInfo queryResult2 = AutoUtil.findNodeInfosByText(context.getRootInActiveWindow(),"添加到通讯录");
            if(queryResult1!=null){
                AutoUtil.performSetText(queryWxIdNode,"8888",record,"重新输入微信号码");
            }else if(queryResult2!=null){
                AutoUtil.performClick(queryResult2,record,"添加到通讯录");
            }
            //发送
            AutoUtil.sleep(2000);
            AccessibilityNodeInfo sendBtn = AutoUtil.fineNodeByIdAndTextCheck(context.getRootInActiveWindow(),"com.tencent.mm:id/gd",null,context,record,"初始状态");
            AutoUtil.performClick(sendBtn,record,"点击[发送]按钮");
            AutoUtil.sleep(2000);
            AutoUtil.performBack(context,record,"发送好友返回");
        }
    }
}
