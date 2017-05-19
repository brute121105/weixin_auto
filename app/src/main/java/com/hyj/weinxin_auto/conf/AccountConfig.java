package com.hyj.weinxin_auto.conf;

import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;

import com.hyj.weinxin_auto.GlobalApplication;
import com.hyj.weinxin_auto.activity.AccountSetActivity;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2017/5/18.
 */

public class AccountConfig {
    public void getAccout(){
        SharedPreferences pf = GlobalApplication.getContext().getSharedPreferences("account",MODE_PRIVATE);

    }
}
