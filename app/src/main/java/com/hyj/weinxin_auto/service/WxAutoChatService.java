package com.hyj.weinxin_auto.service;

import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.alibaba.fastjson.JSON;
import com.hyj.weinxin_auto.WeixinAutoService;
import com.hyj.weinxin_auto.common.Constants;
import com.hyj.weinxin_auto.util.AutoUtil;
import com.hyj.weinxin_auto.util.LogUtil;

import java.util.List;
import java.util.Map;
import java.util.Random;

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

public class WxAutoChatService {
    //String recordAction="";
    //Map<String,String> record = new HashMap<String,String>();
    public void autoChat(AccessibilityEvent event, AccessibilityNodeInfo node,WeixinAutoService context,Map<String,String> record){
        int eventType = event.getEventType();
        //1、消息列表获取信息头像
        List<AccessibilityNodeInfo> msgNumImages= node.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/i4");
        if(msgNumImages!=null&&msgNumImages.size()>0){
            AutoUtil.performClick(AutoUtil.fineNodeByIdAndText(context.getRootInActiveWindow(),"com.tencent.mm:id/afv","杰"),record,"杰",1000);
            sendMsg(context.getRootInActiveWindow(),context,record);
            AutoUtil.performClick(AutoUtil.fineNodeByIdAndText(context.getRootInActiveWindow(),"com.tencent.mm:id/afv","LSan"),record,"LSan",1000);
            sendMsg(context.getRootInActiveWindow(),context,record);
            AutoUtil.performClick(AutoUtil.fineNodeByIdAndText(node,"com.tencent.mm:id/afv","华"),record,"华",1000);
            sendMsg(node,context,record);
            AutoUtil.performClick(AutoUtil.fineNodeByIdAndText(node,"com.tencent.mm:id/afv","Aoo"),record,"Aoo",1000);
            sendMsg(node,context,record);
            AutoUtil.performClick(AutoUtil.fineNodeByIdAndText(node,"com.tencent.mm:id/afv","曼婷"),record,"Aoo",1000);
            sendMsg(node,context,record);
            AutoUtil.performClick(AutoUtil.fineNodeByIdAndText(node,"com.tencent.mm:id/afv","苏苏"),record,"Aoo",1000);
            sendMsg(node,context,record);
            AutoUtil.performClick(AutoUtil.fineNodeByIdAndText(node,"com.tencent.mm:id/afv","zh琪"),record,"Aoo",1000);
            sendMsg(node,context,record);
        }
    }
    public void sendMsg(AccessibilityNodeInfo node,WeixinAutoService context,Map<String,String> record){
        AccessibilityNodeInfo editText = AutoUtil.findNodeInfosById(context.getRootInActiveWindow(),"com.tencent.mm:id/a3b");
        //6、填充第3步已获取消息到输入框
        if(editText!=null){
            editText.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT,AutoUtil.createBuddleText(System.currentTimeMillis()+""));
            AutoUtil.recordAndLog(record,Constants.CHAT_ACTION_05);
        }
        //7、发送
        if(Constants.CHAT_ACTION_05.equals(record.get("recordAction"))){
            AccessibilityNodeInfo sendBtn = AutoUtil.findNodeInfosByText(context.getRootInActiveWindow(),"发送");
            AutoUtil.performClick(sendBtn,record,Constants.CHAT_ACTION_06,3000);
        }

        AccessibilityNodeInfo backBtn = AutoUtil.findNodeInfosById(context.getRootInActiveWindow(),"com.tencent.mm:id/gg");
        AutoUtil.performClick(backBtn,record,"backBtnbackBtn",3000);
        LogUtil.d("chat","69");
    }

}
