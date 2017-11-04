package com.hyj.weinxin_auto.daoModel;

import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * Created by asus on 2017/10/29.
 */

public class CarMsg extends DataSupport{
    private String text;
    private Date createtime;
    private int isUpload;

    public CarMsg(){

    }
    public CarMsg(String text, Date createtime, int isUpload) {
        this.text = text;
        this.createtime = createtime;
        this.isUpload = isUpload;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public int getIsUpload() {
        return isUpload;
    }

    public void setIsUpload(int isUpload) {
        this.isUpload = isUpload;
    }
}
