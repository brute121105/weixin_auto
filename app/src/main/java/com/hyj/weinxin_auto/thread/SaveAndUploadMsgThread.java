package com.hyj.weinxin_auto.thread;

import com.hyj.weinxin_auto.daoModel.CarMsg;
import com.hyj.weinxin_auto.util.LogUtil;

import org.litepal.crud.DataSupport;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 采集信息保存到数据库并上传服务器
 */

public class SaveAndUploadMsgThread implements Runnable {

    private Set<String> msgSet;

    public SaveAndUploadMsgThread(Set<String> msgSet) {
        this.msgSet = msgSet;
    }

    @Override
    public void run() {
        LogUtil.d("SaveAndUploadMsgThread","SaveAndUploadMsgThread-->"+Thread.currentThread().getName()+" ");
       for(String s:msgSet){
           List<CarMsg> queryMsg = DataSupport.where("text=?",s).find(CarMsg.class);
           if(queryMsg!=null&&queryMsg.size()>0){
               System.out.println("-->存在  "+s);
           }else {
               CarMsg carMsg = new CarMsg(s,new Date(),0);
               if(carMsg.save()){
                   System.out.println("--->save success ="+s);
               }
           }
       }
        msgSet.clear();
    }
}
