package com.hyj.weinxin_auto.service;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.hyj.weinxin_auto.WeixinAutoService;
import com.hyj.weinxin_auto.util.AutoUtil;
import com.hyj.weinxin_auto.util.LogUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

public class WxFetchFrDataService {
    //String recordAction="";
    //Map<String,String> record = new HashMap<String,String>();
    List<String> strs = new ArrayList<String>();
    public void autoFetchFrData(AccessibilityEvent event, AccessibilityNodeInfo node,WeixinAutoService context,Map<String,String> record){
        //if(node==null||!record.get("recordAction").equals(Constants.CHAT_LISTENING)) return;
        int eventType = event.getEventType();
        if(node==null){
            LogUtil.d("autoFetchFrData","node 为空；");
            return;
        }
        Set<String> set = new HashSet<String>();
        if(node.getChildCount()>0){
            List<AccessibilityNodeInfo> items = new ArrayList<AccessibilityNodeInfo>();
            List<AccessibilityNodeInfo> items0 = node.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/lr");
            List<AccessibilityNodeInfo> items1 = node.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/b4e");
            if(items0==null&&items1==null){ LogUtil.d("autoFetchFrData","列表数据为空");return;}
            items.addAll(items0);
            items.addAll(items1);
            if(items!=null&&items.size()>0){
                for(AccessibilityNodeInfo item:items){
                    String text = item.getText().toString();
                    set.add(text);
                    if(isExist(strs,text)){
                        System.out.println("重复---->"+text);
                    }else{
                        LogUtil.d("autoFetchFrData",text);
                    }
                }
            }
        }
        //save(set);
        int waitTimes = 4000+(int) (Math.random()*1200);
        AutoUtil.sleep(waitTimes);
        AccessibilityNodeInfo nodeInfo2 = AutoUtil.findNodeInfosById(node,"com.tencent.mm:id/cp7");
        //AccessibilityNodeInfo nodeInfo2 = node.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/cp7").get(0);
        if(nodeInfo2!=null){
            nodeInfo2.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
        }
    }
    public boolean isExist(List<String> strs,String str){
        boolean flag = checkStr(strs,str);
        if(strs.size()<10){
            strs.add(str);
        }else{
            strs.remove(0);
            strs.add(str);
        }
        return flag;
    }
    //判断item是否存在集合
    public boolean checkStr(List<String> strs,String str){
        if(strs!=null&&strs.size()>0){
            for(String s:strs){
                if(str.equals(s)){
                    return true;
                }
            }
        }
        return false;
    }
}
