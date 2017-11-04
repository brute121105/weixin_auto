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
import com.hyj.weinxin_auto.util.LogUtil;

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
        if(nodeInfo==null){
            LogUtil.d("autoFetchFrData","node 为空；");
            return;
        }
        //1、退出当前账号
        if(AutoUtil.checkAction(record,Constants.LOGIN_ACTION_08)||AutoUtil.checkAction(record,Constants.CHAT_LISTENING)){
            AccessibilityNodeInfo exitCurrentAcountBtn = AutoUtil.findNodeInfosByText(nodeInfo,"退出当前帐号");
            AutoUtil.performClick(exitCurrentAcountBtn,record,Constants.LOGIN_ACTION_01,500);
            return;
        }
        //2、确认退出
        if(AutoUtil.checkAction(record,Constants.LOGIN_ACTION_01)){
            AccessibilityNodeInfo quiteConfirmBtn = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/bu8").get(0).getChild(1);
            //AccessibilityNodeInfo quiteConfirmBtn = AutoUtil.findNodeInfosById(nodeInfo,"com.tencent.mm:id/bu8");
            AutoUtil.performClick(quiteConfirmBtn,record,Constants.LOGIN_ACTION_02);
            return;
        }
        //3、更多
        if(AutoUtil.checkAction(record,Constants.LOGIN_ACTION_02)){
            AccessibilityNodeInfo moreBtn = AutoUtil.findNodeInfosByText(nodeInfo,"更多");
            AutoUtil.performClick(moreBtn,record,Constants.LOGIN_ACTION_03,500);
            return;
        }
        //4、切换账号
        if(AutoUtil.checkAction(record,Constants.LOGIN_ACTION_03)){
            List<AccessibilityNodeInfo> changeAccount =  nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/amv");
            if(changeAccount!=null&&changeAccount.size()>0){
                AutoUtil.performClick(changeAccount.get(0).getChild(0),record,Constants.LOGIN_ACTION_04);
            }
            return;
        }
        //5、使用其他方式登录
        if(AutoUtil.checkAction(record,Constants.LOGIN_ACTION_04)){
            AccessibilityNodeInfo changeLoginWayBtn = AutoUtil.findNodeInfosByText(nodeInfo,"使用其他方式登录");
            AutoUtil.performClick(changeLoginWayBtn,record,Constants.LOGIN_ACTION_05,500);
            return;
        }
        //6、输入账号、密码、点击登录
        if(AutoUtil.checkAction(record,Constants.LOGIN_ACTION_05)){
            List<AccessibilityNodeInfo> userNode =  nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/bfm");
            if(userNode!=null&&userNode.size()==1){
                String username = accounts.get(accountIndex)[0];
                AutoUtil.performSetText(userNode.get(0).getChild(1),username,record,Constants.LOGIN_ACTION_06);
            }
            List<AccessibilityNodeInfo> pwdNode =  nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/bfn");
            if(pwdNode!=null&&pwdNode.size()==1){
                AutoUtil.performSetText(pwdNode.get(0).getChild(1),accounts.get(accountIndex)[1],record,Constants.LOGIN_ACTION_07);
            }
            AutoUtil.sleep(500);
            AccessibilityNodeInfo loginBtn = AutoUtil.findNodeInfosById(nodeInfo,"com.tencent.mm:id/bfo");
            AutoUtil.performClick(loginBtn,record,Constants.LOGIN_ACTION_08);
            if(accounts.size()-1==accountIndex){
                accountIndex = 0;
            }else {
                accountIndex = accountIndex+1;
            }
            AutoUtil.recordAndLog(record,Constants.CHAT_LISTENING);
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
