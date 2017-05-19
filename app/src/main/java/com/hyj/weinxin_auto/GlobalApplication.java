package com.hyj.weinxin_auto;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2017/5/18.
 */

public class GlobalApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
