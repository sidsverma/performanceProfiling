package com.android.performanceprofilinglibrary.performance.models;

import android.util.SparseArray;

import com.android.performanceprofilinglibrary.performance.Utilities;

/**
 * Created by Sid on 15/07/16.
 */
public class AroundFunctionalityData {
    private String timestamp;
    private String title;
    private String description;
    private long startTime;
    private long endTime;
    private String result;
    private BasicInfo basicInfo;
    private SparseArray<GeneralPerformance> generalPerformances;
    private SparseArray<CustomAttributeData> customAttributes;

    public AroundFunctionalityData(String title, String description, long startTime, long endTime,
                                   String result, BasicInfo basicInfo,
                                   SparseArray<GeneralPerformance> generalPerformances,
                                   SparseArray<CustomAttributeData> customAttributes) {
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.result = result;
        this.basicInfo = basicInfo;
        this.generalPerformances = generalPerformances;
        this.customAttributes = customAttributes;
        setCurrentTimeStamp();
    }

    public AroundFunctionalityData(String title, String description, long startTime, long endTime,
                                   String result, BasicInfo basicInfo,
                                   SparseArray<GeneralPerformance> generalPerformances) {
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.result = result;
        this.basicInfo = basicInfo;
        this.generalPerformances = generalPerformances;
        setCurrentTimeStamp();
    }

    private void setCurrentTimeStamp() {
        timestamp = Utilities.getCurrentTimestamp();
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public String getResult() {
        return result;
    }

    public BasicInfo getBasicInfo() {
        return basicInfo;
    }

    public SparseArray<GeneralPerformance> getGeneralPerformances() {
        return generalPerformances;
    }

    public SparseArray<CustomAttributeData> getCustomAttributes() {
        return customAttributes;
    }
}
