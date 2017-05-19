package com.hyj.weinxin_auto;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/16.
 */

public class WeixinAutoHandler {
    public Map<String, String> controller = new HashMap<String, String>();
    private static WeixinAutoHandler weixinAutoHandler = new WeixinAutoHandler();
    private WeixinAutoHandler(){}
    public static WeixinAutoHandler getInstance(){
        return weixinAutoHandler;
    }
    //自动登录
    public static boolean IS_AUTO_LOGIN=false;
    //自动聊天
    public static boolean IS_AUTO_CHAT=false;
    //自动发朋友圈
    public static boolean IS_AUTO_SENDFRMSG=false;
    //自动添加好友
    public static boolean IS_AUTO_ADDFR=false;
    //自动抓取朋友圈信息
    public static boolean IS_AUTO_FETCHDATA=false;

}
