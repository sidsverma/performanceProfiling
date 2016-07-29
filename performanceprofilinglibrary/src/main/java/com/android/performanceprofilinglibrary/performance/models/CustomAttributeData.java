package com.android.performanceprofilinglibrary.performance.models;

/**
 * Created by Sid on 15/07/16.
 */
public class CustomAttributeData {
    public final String key;
    public final String value;

    public CustomAttributeData(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
