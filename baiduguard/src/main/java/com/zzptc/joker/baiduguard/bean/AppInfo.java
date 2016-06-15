package com.zzptc.joker.baiduguard.bean;

import android.graphics.drawable.Drawable;

/**
 * Created by joker on 2016/5/30/030.
 * 要显示的用户应用程序的bean
 */
public class AppInfo {

//    应用图标
    private Drawable appIcon;

//    应用名字
    private String appName;

//    应用所用的内存
    private String appMemory;

//    应用是否被勾选了
    private Boolean isChecked=true;

//    应用包名
    private String packageName;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppMemory() {
        return appMemory;
    }

    public void setAppMemory(String appMemory) {
        this.appMemory = appMemory;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "appIcon=" + appIcon +
                ", appName='" + appName + '\'' +
                ", appMemory='" + appMemory + '\'' +
                ", isChecked=" + isChecked +
                ", packageName='" + packageName + '\'' +
                '}';
    }
}
