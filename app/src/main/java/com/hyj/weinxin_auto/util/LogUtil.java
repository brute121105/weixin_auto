package com.hyj.weinxin_auto.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/5/15.
 */

public class LogUtil {
    //记录日志到sd卡
    public static void d(String tab,String msg){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTime = sdf.format(new Date());
        String logMsg = dateTime+" "+tab+"---->"+msg;
        //以天为单位生成日志文件
        System.out.println(logMsg);
        FileUtil.writeContent2File("/sdcard/A_hyj_log/","log_"+dateTime.substring(0,10)+".txt",logMsg);
    }
}
