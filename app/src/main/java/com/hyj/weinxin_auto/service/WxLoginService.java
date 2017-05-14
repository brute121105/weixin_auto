package com.hyj.weinxin_auto.service;

import android.os.Bundle;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * Created by asus on 2017/5/12.
 */

public class WxLoginService {
    public void autoLogin(AccessibilityEvent event,AccessibilityNodeInfo nodeInfo){
        int eventType = event.getEventType();
        System.out.println("WeixinAutoService-->"+ JSON.toJSONString(nodeInfo));
        if(nodeInfo==null) return;
        //退出当前账号
        List<AccessibilityNodeInfo> quiteCurrentAcount =  nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/bds");
        if(quiteCurrentAcount!=null&&quiteCurrentAcount.size()>0){
            System.out.println("WeixinAutoService-->quiteCurrentAcount"+quiteCurrentAcount.size());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            quiteCurrentAcount.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
        //确认退出
        List<AccessibilityNodeInfo> quiteConfirm =  nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/abz");
        if(quiteConfirm!=null&&quiteConfirm.size()>0){
            System.out.println("WeixinAutoService-->quiteConfirm"+quiteConfirm.size());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            quiteConfirm.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
        //切换账号
        List<AccessibilityNodeInfo> changeAccount =  nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/alf");
        if(changeAccount!=null&&changeAccount.size()>0){
            System.out.println("WeixinAutoService-->changeAccount"+changeAccount.size());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            changeAccount.get(0).getChild(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
        //更多
        List<AccessibilityNodeInfo> moreBtn =  nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/bdm");
        if(moreBtn!=null&&moreBtn.size()>0){
            System.out.println("WeixinAutoService-->moreBtn"+moreBtn.size());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            moreBtn.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }

        //使用其他方式登录
        List<AccessibilityNodeInfo> changeWayNode =  nodeInfo.findAccessibilityNodeInfosByText("使用其他方式登录");
        if(changeWayNode!=null&&changeWayNode.size()>0){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            changeWayNode.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
        //输入账号密码登录
        List<AccessibilityNodeInfo> loginNode =  nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/gr");
        if(loginNode!=null&&loginNode.size()==2){
            System.out.println("WeixinAutoService-->"+loginNode.size());
            Bundle username = new Bundle();
            Bundle pwd = new Bundle();
            username.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, "szinfo0002");
            pwd.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, "szinfo0002");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            loginNode.get(0).performAction(AccessibilityNodeInfo.ACTION_SET_TEXT,username);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            loginNode.get(1).performAction(AccessibilityNodeInfo.ACTION_SET_TEXT,pwd);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<AccessibilityNodeInfo> loginBtn =  nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/bd4");
            if(loginBtn!=null&&loginBtn.size()>0){
                System.out.println("WeixinAutoService-->loginBtn"+loginBtn.size());
                loginBtn.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }

        }

        System.out.println("WeixinAutoService-->"+eventType);
        switch (eventType){
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                System.out.println("WeixinAutoService-->TYPE_WINDOW_STATE_CHANGED");
                break;
            case AccessibilityEvent.TYPE_WINDOWS_CHANGED:
                System.out.println("WeixinAutoService-->TYPE_WINDOWS_CHANGED");
                break;
        }
    }
}
