package com.hyj.weinxin_auto.service;

import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.hyj.weinxin_auto.WeixinAutoService;
import com.hyj.weinxin_auto.common.Constants;
import com.hyj.weinxin_auto.util.AutoUtil;

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
            AccessibilityNodeInfo addFrBtn0 = AutoUtil.findNodeInfosById(node,"com.tencent.mm:id/f_");
            AutoUtil.performClick(addFrBtn0,record,"点击右上角[+]",500);
            AccessibilityNodeInfo addFrBtn1=null;
            int count=0;
            while (count<6){
                node = context.getRootInActiveWindow();
                addFrBtn1 = AutoUtil.fineNodeByIdAndText(node,"com.tencent.mm:id/fa","添加朋友");
                if(addFrBtn1!=null)
                    break;
                count = count +1;
                AutoUtil.sleep(500*count);
            }
            if(addFrBtn1==null)
                record.put("recordAction","初始状态");
            System.out.println("addfr-->"+addFrBtn1);
            AutoUtil.performClick(addFrBtn1,record,"点击右上角[添加好友]");
        }
    }
}
