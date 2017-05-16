package com.hyj.weinxin_auto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    WeixinAutoHandler handler = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = WeixinAutoHandler.getInstance();
        CheckBox autoLogin = (CheckBox)this.findViewById(R.id.auto_login);
        CheckBox autoChat = (CheckBox)this.findViewById(R.id.auto_chat);
        CheckBox autoSendFrMsg = (CheckBox)this.findViewById(R.id.auto_sendFrMsg);
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
    }
}
