package com.hyj.weinxin_auto.service;

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
 * Created by asus on 2017/5/12.
 * 只需监听typeWindowStateChanged
 * 1、点击发现
 * 2、点击朋友圈
 * 3、点击右上角[发送朋友圈]
 * 4、点击[从相册选择]
 * 5、选照片
 * 6、点击右上角[完成]
 * 7、点击右上角[发送]
 */

public class WxSendFrMsgService {
    //String recordAction="";
    //Map<String,String> record = new HashMap<String,String>();
    public void autoSendFrMsg(AccessibilityEvent event, AccessibilityNodeInfo nodeInfo,WeixinAutoService context,Map<String,String> record){
        int eventType = event.getEventType();
        if(nodeInfo==null||!record.get("recordAction").contains("SENDFR_")) return;
        System.out.println(record+"node-->eventType"+ eventType);
        List<AccessibilityNodeInfo> wxWdn = nodeInfo.findAccessibilityNodeInfosByText("微信");
        if(wxWdn!=null){
            AccessibilityNodeInfo findBtn = AutoUtil.findNodeInfosByText(nodeInfo,"发现");
            //1、点击发现
            if(findBtn!=null&&(record.get("recordAction").equals(Constants.CHAT_ACTION_FORWD)||Constants.SENDFR_ACTION_07.equals(record.get("recordAction")))){
                AutoUtil.performClick(findBtn,record, Constants.SENDFR_ACTION_01);
            }
            //2、点击朋友圈
            AccessibilityNodeInfo albumBtn = AutoUtil.findNodeInfosByText(nodeInfo,"朋友圈");
            if(Constants.SENDFR_ACTION_01.equals(record.get("recordAction"))&&albumBtn!=null){
                AutoUtil.performClick(albumBtn,record,Constants.SENDFR_ACTION_02);
                return;
            }
        }
        //3、点击右上角[发送朋友圈]
        List<AccessibilityNodeInfo> pyqWdn = nodeInfo.findAccessibilityNodeInfosByText("朋友圈");
        if(eventType==AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED&&pyqWdn!=null){
            List<AccessibilityNodeInfo> photoBtn = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/f_");
            if(Constants.SENDFR_ACTION_02.equals(record.get("recordAction"))&&photoBtn!=null&&photoBtn.size()>0){
                AutoUtil.performClick(photoBtn.get(0),record,Constants.SENDFR_ACTION_03);
                return;
            }
            //4、点击[从相册选择]
            List<AccessibilityNodeInfo> selectFromAlbumBtn = nodeInfo.findAccessibilityNodeInfosByText("从相册选择");
            if(Constants.SENDFR_ACTION_03.equals(record.get("recordAction"))&&selectFromAlbumBtn!=null&&selectFromAlbumBtn.size()>0){
                AutoUtil.performClick(selectFromAlbumBtn.get(0),record,Constants.SENDFR_ACTION_04);
                return;
            }
        }
        //5、选照片
        if(Constants.SENDFR_ACTION_04.equals(record.get("recordAction"))&&eventType==AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED){
            List<AccessibilityNodeInfo> pictureAndVideoWdn = nodeInfo.findAccessibilityNodeInfosByText("图片和视频");
            if(pictureAndVideoWdn!=null){
                List<AccessibilityNodeInfo> selectPhotoBtn = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/b6u");
                if(selectPhotoBtn!=null&&selectPhotoBtn.size()>0){
                    AutoUtil.performClick(selectPhotoBtn.get(0),record,Constants.SENDFR_ACTION_05);
                    AutoUtil.performClick(selectPhotoBtn.get(1),record,Constants.SENDFR_ACTION_05);
                }
        //6、点击右上角[完成]
        List<AccessibilityNodeInfo> selectFinishBtn = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/gd");
        if(Constants.SENDFR_ACTION_05.equals(record.get("recordAction"))&&selectFinishBtn!=null&&selectFinishBtn.size()>0){
            AutoUtil.performClick(selectFinishBtn.get(0),record,Constants.SENDFR_ACTION_06,1500);
            return;
        }
            }
        }
        System.out.println(eventType+"--------666>"+record.get("recordAction")+"--->"+AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED);
        //7、点击右上角[发送]
        if(Constants.SENDFR_ACTION_06.equals(record.get("recordAction"))&&eventType==AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED){
            List<AccessibilityNodeInfo> inputMsg = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/cpe");
            List<AccessibilityNodeInfo> sendMsgBtn = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/gd");
            System.out.println("-------->进入"+inputMsg);
            if(inputMsg!=null&&inputMsg.size()>0){
                System.out.println("-------->进入进入进入");
                inputMsg.get(0).performAction(AccessibilityNodeInfo.ACTION_SET_TEXT,AutoUtil.createBuddleText(record.get("rMsg")));
                AutoUtil.performClick(sendMsgBtn.get(0),record,Constants.SENDFR_ACTION_07,1000);
                AutoUtil.performBack(context,record,Constants.SENDFR_ACTION_08);
                //AutoUtil.recordAndLog(record,Constants.SENDFR_ACTION_08);
                AutoUtil.recordAndLog(record,Constants.CHAT_LISTENING);
            }
        }


    }
}
