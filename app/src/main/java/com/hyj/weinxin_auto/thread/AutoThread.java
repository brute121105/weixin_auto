package com.hyj.weinxin_auto.thread;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityNodeInfo;

import com.hyj.weinxin_auto.ParseRootUtil;
import com.hyj.weinxin_auto.util.AutoUtil;
import com.hyj.weinxin_auto.util.LogUtil;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by asus on 2017/10/28.
 */

public class AutoThread implements Runnable {
    AccessibilityService context;
    Map<String,String> record = new HashMap<String,String>();
    public AutoThread(AccessibilityService context){
        this.context = context;
    }
    boolean isReflesh = false;//标记上拉刷新
    Set<String> msgSet = new HashSet<String>();
    @Override
    public void run() {
        while (true){
            AutoUtil.sleep(1500);
            LogUtil.d("AutoThread","AutoThread线程-->"+Thread.currentThread().getName()+" ");
            AccessibilityNodeInfo root = context.getRootInActiveWindow();
            if(root==null){
                System.out.println("-->root is null");
                AutoUtil.sleep(500);
                continue;
            }
            if(!root.getPackageName().equals("com.tencent.mm")) continue;
           /* List<List<NodeAttr>> treeNodes =  ParseRootUtil.createLevelNodes(root);

            for(List<NodeAttr> levelNodes :treeNodes){
                for(NodeAttr levelNode:levelNodes){
                    System.out.println("--->"+JSON.toJSON(levelNode));
                }
            }*/
            if(checkNodeByText(root,"022","用短信验证码登录")){
                AccessibilityNodeInfo node1 = ParseRootUtil.getNodePath(root,"01");
                AutoUtil.performClick(node1,record,"点击更多切换账号");
            }
            AccessibilityNodeInfo node2 = ParseRootUtil.getNodePath(root,"01000");
            AutoUtil.performClick(node2,record,"点击切换账号",1000);

            if(checkNodeByText(root,"0030","手机号登录")){
                AccessibilityNodeInfo node3 = ParseRootUtil.getNodePath(root,"0033");
                AutoUtil.performClick(node3,record,"用微信号/QQ号/邮箱登录",1000);
            }

            if(checkNodeByText(root,"00311","请填写微信号/QQ号/邮箱")){
                AccessibilityNodeInfo wxidNode = ParseRootUtil.getNodePath(root,"00311");
                AccessibilityNodeInfo pwdNode = ParseRootUtil.getNodePath(root,"00321");//特殊节点，不能获取text为“密码”的节点，需要获取"isEditable":true节点
                AccessibilityNodeInfo loginNode = ParseRootUtil.getNodePath(root,"0034");
                AutoUtil.performSetText(wxidNode,"nnk4869",record,"输入微信号");
                AutoUtil.performSetText(pwdNode,"huang121105",record,"输入密码");
                AutoUtil.performClick(loginNode,record,"登录2",1000);
            }

            if(!AutoUtil.checkAction(record,"点击搜索")){
                AccessibilityNodeInfo node1 = ParseRootUtil.getNodePath(root,"06");
                AutoUtil.performClick(node1,record,"点击搜索");
            }
            if(AutoUtil.checkAction(record,"点击搜索")){
                AccessibilityNodeInfo node = ParseRootUtil.getNodePath(root,"02");
                if("搜索".equals(node.getText()+"")){
                    AutoUtil.performSetText(node,"bt87165888",record,"输入搜索微信号");
                }
            }
            if(AutoUtil.checkAction(record,"输入搜索微信号")){
                AccessibilityNodeInfo wxTextNode = AutoUtil.findNodeInfosByText(root,"微信号: bt87165888");
                AutoUtil.performClick(wxTextNode,record,"点击搜索结果",1000);
            }


            if(checkNodeByContentDesc(root,"003","聊天信息")){
                AccessibilityNodeInfo node = ParseRootUtil.getNodePath(root,"003");
                AutoUtil.performClick(node,record,"点击右上角人像");
            }
            if(AutoUtil.checkAction(record,"点击右上角人像")){
                AccessibilityNodeInfo nicknameTextNode = AutoUtil.findNodeInfosByText(root,"北通信息网1号");
                AutoUtil.performClick(nicknameTextNode,record,"点击昵称");
            }
            AccessibilityNodeInfo nicknameTextNode = AutoUtil.findNodeInfosByText(root,"个人相册");
            if(AutoUtil.checkAction(record,"点击昵称")&&nicknameTextNode!=null){
                AutoUtil.recordAndLog(record,"个人相册");
                AutoUtil.clickXY(382,708);
                AutoUtil.sleep(5000);
                isReflesh = false;
            }

            AccessibilityNodeInfo listView =  AutoUtil.findNodeInfosById(root,"com.tencent.mm:id/czj");//朋友圈滚动列表
            if(listView!=null){
                /**
                 * 上拉刷新两次操作
                 */
                if(!isReflesh&&listView.performAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD)){
                    AutoUtil.sleep(2000);
                    listView.performAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD);
                    AutoUtil.recordAndLog(record,"上拉刷新");
                    isReflesh = true;
                }

                if(msgSet.size()<30){
                    /**
                     * 获取朋友圈列表文本
                     */
                    List<AccessibilityNodeInfo> textNodes = new ArrayList<AccessibilityNodeInfo>();
                    textNodes.addAll(root.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/ba1"));//朋友圈有照片文本
                    textNodes.addAll(root.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/o0"));//朋友圈无照片文本
                    if(textNodes!=null&&textNodes.size()>0){
                        for(AccessibilityNodeInfo txtNode:textNodes){
                            System.out.println("--->"+txtNode.getText());
                            msgSet.add(txtNode.getText()+"");
                        }
                    }
                    /**
                     * 滚动，并且滚动标志加1
                     */
                    if(listView.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)){
                        AutoUtil.sleep(3000);
                    }
                }else {
                    if(!AutoUtil.checkAction(record,"线程处理数据")&&msgSet.size()>0){
                        AutoUtil.recordAndLog(record,"线程处理数据");
                        new Thread(new SaveAndUploadMsgThread(msgSet)).start();
                    }
                }
            }
        }
    }

    private boolean checkNodeByText(AccessibilityNodeInfo root,String path ,String text){
        AccessibilityNodeInfo node = ParseRootUtil.getNodePath(root,path);
        if(node!=null&&text.equals(node.getText()+""))
            return true;
        else
            return false;
    }
    private boolean checkNodeByContentDesc(AccessibilityNodeInfo root,String path ,String text){
        AccessibilityNodeInfo node = ParseRootUtil.getNodePath(root,path);
        if(node!=null&&text.equals(node.getContentDescription()+""))
            return true;
        else
            return false;
    }
}
