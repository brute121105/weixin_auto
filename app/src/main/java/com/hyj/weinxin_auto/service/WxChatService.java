package com.hyj.weinxin_auto.service;

import android.accessibilityservice.AccessibilityService;
import android.os.Bundle;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.alibaba.fastjson.JSON;
import com.hyj.weinxin_auto.WeixinAutoService;
import com.hyj.weinxin_auto.common.Constants;
import com.hyj.weinxin_auto.util.AutoUtil;

import java.util.HashMap;
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

public class WxChatService {
    //String recordAction="";
    //Map<String,String> record = new HashMap<String,String>();
    public void autoChat(AccessibilityEvent event, AccessibilityNodeInfo node,WeixinAutoService context,Map<String,String> record){
        if(node==null||!record.get("recordAction").equals(Constants.CHAT_LISTENING)) return;
        int eventType = event.getEventType();
        //1、消息列表获取信息头像
        List<AccessibilityNodeInfo> msgNumImages= node.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/i4");
        if(msgNumImages!=null&&msgNumImages.size()>0){
            AutoUtil.recordAndLog(record,Constants.CHAT_ACTION_01);
        }
        if(Constants.CHAT_ACTION_01.equals(record.get("recordAction"))){
            //2、获取消息条数、进入聊天窗口
            AccessibilityNodeInfo msgNumImage = msgNumImages.get(0);
            int msgNum = Integer.parseInt(msgNumImage.getText().toString());
            AutoUtil.performClick(msgNumImage,record,Constants.CHAT_ACTION_02,1000);
            //3、获取当前聊天窗口信息
            AccessibilityNodeInfo nodeInfo = context.getRootInActiveWindow();
            //昵称
            AccessibilityNodeInfo nickNameNode =AutoUtil.findNodeInfosById(nodeInfo,"com.tencent.mm:id/gh");
            String nickName ="";
            if(nickNameNode!=null){
                nickName = nickNameNode.getText().toString();
            }
            //消息
            List<AccessibilityNodeInfo> receviceMsgs = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/if");
            String rMsg = AutoUtil.getChatWindowMsg(receviceMsgs,msgNum);
            //4、返回消息列表
            AutoUtil.performBack(context,record,Constants.CHAT_ACTION_03);
            //收到指定人消息，转发朋友圈
            if(Constants.MSG_FROM_NAME.equals(nickName)){
                record.put("rMsg",rMsg);
                AutoUtil.recordAndLog(record,Constants.CHAT_ACTION_FORWD);
                return;
            }
            if(Constants.CHAT_ACTION_03.equals(record.get("recordAction"))){
                AutoUtil.sleep(300);
                //5、打开转发聊天窗口
                AccessibilityNodeInfo personNode = AutoUtil.fineNodeByIdAndText(nodeInfo,"com.tencent.mm:id/afv","杰");
                AutoUtil.performClick(personNode,record,Constants.CHAT_ACTION_04,300);
                AccessibilityNodeInfo editText = AutoUtil.findNodeInfosById(context.getRootInActiveWindow(),"com.tencent.mm:id/a3b");
                //6、填充第3步已获取消息到输入框
                if(editText!=null){
                    editText.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT,AutoUtil.createBuddleText(rMsg));
                    AutoUtil.recordAndLog(record,Constants.CHAT_ACTION_05);
                }
                //7、发送
                if(Constants.CHAT_ACTION_05.equals(record.get("recordAction"))){
                    AccessibilityNodeInfo sendBtn = AutoUtil.findNodeInfosByText(context.getRootInActiveWindow(),"发送");
                    AutoUtil.performClick(sendBtn,record,Constants.CHAT_ACTION_06);
                    AutoUtil.performBack(context,record,Constants.CHAT_ACTION_07);
                    //AutoUtil.recordAndLog(record,Constants.CHAT_ACTION_07);
                    AutoUtil.recordAndLog(record,Constants.CHAT_LISTENING);
                }
            }
        }

    }
}
