package com.zzptc.joker.baiduguard;

import android.app.Application;

import org.xutils.x;

/**
 * Created by joker on 2016/4/27/027.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
//        x.Ext.setDebug(BuildConfig.DEBUG);

    }
}
