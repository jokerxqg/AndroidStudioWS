package com.zzptc.joker.baiduguard.bean;

import java.io.Serializable;

/**
 * Created by joker on 2016/4/27/027.
 */
public class UpdateInfo implements Serializable{
    private int versionCode;
    private String downloadUrl;
    private String description;

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
