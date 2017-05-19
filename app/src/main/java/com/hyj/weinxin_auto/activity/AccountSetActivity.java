package com.hyj.weinxin_auto.activity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyj.weinxin_auto.GlobalApplication;
import com.hyj.weinxin_auto.R;

public class AccountSetActivity extends AppCompatActivity {
    private SharedPreferences.Editor shPrefEdit;
    private SharedPreferences shPref;
    EditText usrname1;
    EditText pwd1;
    EditText usrname2;
    EditText pwd2;
    EditText usrname3;
    EditText pwd3;
    EditText usrname4;
    EditText pwd4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_set);
        shPref = GlobalApplication.getContext().getSharedPreferences("account",MODE_PRIVATE);
        usrname1 = (EditText)findViewById(R.id.username1);
        pwd1 = (EditText)findViewById(R.id.pwd1);
        usrname2 = (EditText)findViewById(R.id.username2);
        pwd2 = (EditText)findViewById(R.id.pwd2);
        usrname3 = (EditText)findViewById(R.id.username3);
        pwd3 = (EditText)findViewById(R.id.pwd3);
        usrname4 = (EditText)findViewById(R.id.username4);
        pwd4 = (EditText)findViewById(R.id.pwd4);
        Button save = (Button)findViewById(R.id.account_save);
        Button back = (Button)findViewById(R.id.account_back);

        String u1 = shPref.getString("username1","");
        String p1 = shPref.getString("pwd1","");
        String u2 = shPref.getString("username2","");
        String p2 = shPref.getString("pwd2","");
        String u3 = shPref.getString("username3","");
        String p3 = shPref.getString("pwd3","");
        String u4 = shPref.getString("username4","");
        String p4 = shPref.getString("pwd4","");
        usrname1.setText(u1);
        pwd1.setText(p1);
        usrname2.setText(u2);
        pwd2.setText(p2);
        usrname3.setText(u3);
        pwd3.setText(p3);
        usrname4.setText(u4);
        pwd4.setText(p4);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u1 = usrname1.getText().toString().trim();
                String p1 = pwd1.getText().toString().trim();
                String u2 = usrname2.getText().toString().trim();
                String p2 = pwd2.getText().toString().trim();
                String u3 = usrname3.getText().toString().trim();
                String p3 = pwd3.getText().toString().trim();
                String u4 = usrname4.getText().toString().trim();
                String p4 = pwd4.getText().toString().trim();
                shPrefEdit = shPref.edit();

                shPrefEdit.putString("username1",u1);
                shPrefEdit.putString("pwd1",p1);
                shPrefEdit.putString("username2",u2);
                shPrefEdit.putString("pwd2",p2);
                shPrefEdit.putString("username3",u3);
                shPrefEdit.putString("pwd3",p3);
                shPrefEdit.putString("username4",u4);
                shPrefEdit.putString("pwd4",p4);
                shPrefEdit.commit();
                Toast.makeText(GlobalApplication.getContext(),"保存成功",Toast.LENGTH_SHORT);
                //shPref.getString("username1","");
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
