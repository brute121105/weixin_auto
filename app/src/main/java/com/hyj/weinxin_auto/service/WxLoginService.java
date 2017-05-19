package com.hyj.weinxin_auto.service;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.alibaba.fastjson.JSON;
import com.hyj.weinxin_auto.GlobalApplication;
import com.hyj.weinxin_auto.WeixinAutoService;
import com.hyj.weinxin_auto.common.Constants;
import com.hyj.weinxin_auto.util.AutoUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.transform.sax.SAXSource;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by asus on 2017/5/12.
 */

public class WxLoginService {
    int accountIndex;
    public WxLoginService() {
        this.accountIndex = 0;
    }
    public void autoLogin(AccessibilityEvent event, AccessibilityNodeInfo nodeInfo, WeixinAutoService context, Map<String,String> record){
        List<String[]> accounts = this.getAccount();
        int eventType = event.getEventType();
        if(nodeInfo==null) return;
        //1、退出当前账号
        if(AutoUtil.checkAction(record,Constants.LOGIN_ACTION_08)||AutoUtil.checkAction(record,Constants.CHAT_LISTENING)){
            AccessibilityNodeInfo exitCurrentAcountBtn = AutoUtil.findNodeInfosById(nodeInfo,"com.tencent.mm:id/bds");
            AutoUtil.performClick(exitCurrentAcountBtn,record,Constants.LOGIN_ACTION_01,500);
        }
        //2、确认退出
        if(AutoUtil.checkAction(record,Constants.LOGIN_ACTION_01)){
            AccessibilityNodeInfo quiteConfirmBtn = AutoUtil.findNodeInfosById(nodeInfo,"com.tencent.mm:id/abz");
            AutoUtil.performClick(quiteConfirmBtn,record,Constants.LOGIN_ACTION_02);
        }
        //3、更多
        if(AutoUtil.checkAction(record,Constants.LOGIN_ACTION_02)){
            AccessibilityNodeInfo moreBtn = AutoUtil.findNodeInfosById(nodeInfo,"com.tencent.mm:id/bdm");
            AutoUtil.performClick(moreBtn,record,Constants.LOGIN_ACTION_03,500);
        }
        //4、切换账号
        if(AutoUtil.checkAction(record,Constants.LOGIN_ACTION_03)){
            List<AccessibilityNodeInfo> changeAccount =  nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/alf");
            if(changeAccount!=null&&changeAccount.size()>0){
                AutoUtil.performClick(changeAccount.get(0).getChild(0),record,Constants.LOGIN_ACTION_04);
            }
        }
        //5、使用其他方式登录
        if(AutoUtil.checkAction(record,Constants.LOGIN_ACTION_04)){
            AccessibilityNodeInfo changeLoginWayBtn = AutoUtil.findNodeInfosByText(nodeInfo,"使用其他方式登录");
            AutoUtil.performClick(changeLoginWayBtn,record,Constants.LOGIN_ACTION_05,500);
        }
        //6、输入账号、密码、点击登录
        if(AutoUtil.checkAction(record,Constants.LOGIN_ACTION_05)){
            List<AccessibilityNodeInfo> loginNode =  nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/gr");
            if(loginNode!=null&&loginNode.size()==2){
                AutoUtil.performSetText(loginNode.get(0),accounts.get(accountIndex)[0],record,Constants.LOGIN_ACTION_06);
                AutoUtil.sleep(500);
                AutoUtil.performSetText(loginNode.get(1),accounts.get(accountIndex)[1],record,Constants.LOGIN_ACTION_07);
                AccessibilityNodeInfo loginBtn = AutoUtil.findNodeInfosById(nodeInfo,"com.tencent.mm:id/bd4");
                if(loginBtn!=null){
                    AutoUtil.performClick(loginBtn,record,Constants.LOGIN_ACTION_08);
                    accountIndex = accountIndex+1;
                    AutoUtil.recordAndLog(record,Constants.CHAT_LISTENING);
                }
            }
        }
    }
    //读取配置账号
    public List<String[]> getAccount(){
        List<String[]> accounts = new ArrayList<String[]>();
        SharedPreferences shPref = GlobalApplication.getContext().getSharedPreferences("account",MODE_PRIVATE);
        String u1 = shPref.getString("username1","");
        String p1 = shPref.getString("pwd1","");
        String u2 = shPref.getString("username2","");
        String p2 = shPref.getString("pwd2","");
        String u3 = shPref.getString("username3","");
        String p3 = shPref.getString("pwd3","");
        String u4 = shPref.getString("username4","");
        String p4 = shPref.getString("pwd4","");
        if(u1!=null&&!"".equals(u1))
            accounts.add(new String[]{u1,p1});
        if(u2!=null&&!"".equals(u2))
            accounts.add(new String[]{u2,p2});
        if(u3!=null&&!"".equals(u3))
            accounts.add(new String[]{u3,p3});
        if(u4!=null&&!"".equals(u4))
            accounts.add(new String[]{u4,p4});
        return accounts;

    }
}
