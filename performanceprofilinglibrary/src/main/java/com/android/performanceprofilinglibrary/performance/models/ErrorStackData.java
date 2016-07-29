package com.android.performanceprofilinglibrary.performance.models;

import android.app.ApplicationErrorReport;
import android.util.SparseArray;

import com.android.performanceprofilinglibrary.performance.Utilities;

/**
 * Created by Sid on 15/07/16.
 */
public class ErrorStackData {
    private String timestamp;
    private BasicInfo basicInfo;
//    private String stackTrace;
    private int priority;
    private GeneralPerformance generalPerformances;
    private ApplicationErrorReport.CrashInfo crashInfo;
    private SparseArray<CustomAttributeData> customAttributes;

    public ErrorStackData(BasicInfo basicInfo, int priority,
                          GeneralPerformance generalPerformances,
                          ApplicationErrorReport.CrashInfo crashInfo,
                          SparseArray<CustomAttributeData> customAttributes) {
        this.basicInfo = basicInfo;
//        this.stackTrace = stackTrace;
        this.priority = priority;
        this.generalPerformances = generalPerformances;
        this.crashInfo = crashInfo;
        this.customAttributes = customAttributes;
        setCurrentTimeStamp();
    }

    public ErrorStackData(BasicInfo basicInfo, int priority,
                          GeneralPerformance generalPerformances,
                          ApplicationErrorReport.CrashInfo crashInfo) {
        this.basicInfo = basicInfo;
//        this.stackTrace = stackTrace;
        this.priority = priority;
        this.generalPerformances = generalPerformances;
        this.crashInfo = crashInfo;
        setCurrentTimeStamp();
    }

    public ErrorStackData(int priority,
                          GeneralPerformance generalPerformances,
                          ApplicationErrorReport.CrashInfo crashInfo) {
        this.priority = priority;
        this.generalPerformances = generalPerformances;
        this.crashInfo = crashInfo;
        setCurrentTimeStamp();
    }

    private void setCurrentTimeStamp() {
        timestamp = Utilities.getCurrentTimestamp();
    }

    public String getTimestamp() {
        return timestamp;
    }

    public BasicInfo getBasicInfo() {
        return basicInfo;
    }

    public int getPriority() {
        return priority;
    }

    public GeneralPerformance getGeneralPerformances() {
        return generalPerformances;
    }

    public ApplicationErrorReport.CrashInfo getCrashInfo() {
        return crashInfo;
    }

    public SparseArray<CustomAttributeData> getCustomAttributes() {
        return customAttributes;
    }
}
