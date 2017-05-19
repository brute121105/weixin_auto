package com.hyj.weinxin_auto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.hyj.weinxin_auto.activity.AccountSetActivity;
import com.hyj.weinxin_auto.util.GetPermissionUtil;

public class MainActivity extends AppCompatActivity {
    WeixinAutoHandler handler = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GetPermissionUtil.getReadAndWriteContactPermision(MainActivity.this,MainActivity.this);
        handler = WeixinAutoHandler.getInstance();
        CheckBox autoLogin = (CheckBox)this.findViewById(R.id.auto_login);
        CheckBox autoChat = (CheckBox)this.findViewById(R.id.auto_chat);
        CheckBox autoSendFrMsg = (CheckBox)this.findViewById(R.id.auto_sendFrMsg);
        CheckBox autoFetchData = (CheckBox)this.findViewById(R.id.auto_fetchData);
        CheckBox autoAddFr = (CheckBox)this.findViewById(R.id.auto_AddFr);
        Button accountSetBtn = (Button)this.findViewById(R.id.login_set);
        accountSetBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent account = new Intent(MainActivity.this, AccountSetActivity.class);
                startActivity(account);
            }
        });
        autoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                handler.IS_AUTO_LOGIN=isChecked;
                Toast.makeText(MainActivity.this,isChecked?"开启自动登录":"关闭自动登录",Toast.LENGTH_SHORT).show();
            }
        });
        autoChat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                handler.IS_AUTO_CHAT = isChecked;
                Toast.makeText(MainActivity.this,isChecked?"开启自动聊天":"关闭自动聊天",Toast.LENGTH_SHORT).show();
            }
        });
        autoSendFrMsg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                handler.IS_AUTO_SENDFRMSG=isChecked;
                Toast.makeText(MainActivity.this,isChecked?"开启自动发朋友圈":"关闭自动发朋友圈",Toast.LENGTH_SHORT).show();
            }
        });
        autoFetchData.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                handler.IS_AUTO_FETCHDATA=isChecked;
                Toast.makeText(MainActivity.this,isChecked?"开启自动抓取朋友圈数据":"关闭自动抓取朋友圈数据",Toast.LENGTH_SHORT).show();
            }
        });
        autoAddFr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                handler.IS_AUTO_ADDFR=isChecked;
                Toast.makeText(MainActivity.this,isChecked?"开启自动加好友":"关闭自动加好友",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
