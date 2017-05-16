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
    public static boolean IS_AUTO_LOGIN=false;
    public static boolean IS_AUTO_CHAT=false;
    public static boolean IS_AUTO_SENDFRMSG=false;
    public static boolean IS_AUTO_ADDFR=false;
}
