package com.android.performanceprofilinglibrary.performance.models;

/**
 * Created by Sid on 15/07/16.
 */
public class BasicInfo {
    private String appName;
    private String libVersion;

    public BasicInfo(String appName, String libVersion) {
        this.appName = appName;
        this.libVersion = libVersion;
    }

    public String getAppName() {
        return appName;
    }

    public String getLibVersion() {
        return libVersion;
    }
}
