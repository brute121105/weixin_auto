package com.hyj.weinxin_auto.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyj.weinxin_auto.GlobalApplication;
import com.hyj.weinxin_auto.R;

public class WxidSetActivity extends AppCompatActivity {
    EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxid_set);
        final SharedPreferences sharedPreferences = GlobalApplication.getContext().getSharedPreferences("wxids",MODE_PRIVATE);
        String wxid = sharedPreferences.getString("wxid","");
        et = (EditText)findViewById(R.id.wxid_text);
        et.setText(wxid);
        Button saveBtn = (Button)findViewById(R.id.wxid_save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor= sharedPreferences.edit();
                String text = et.getText().toString();
                editor.putString("wxid",text);
                editor.commit();
                //String wxid = sharedPreferences.getString("wxid","");
                Toast.makeText(WxidSetActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
