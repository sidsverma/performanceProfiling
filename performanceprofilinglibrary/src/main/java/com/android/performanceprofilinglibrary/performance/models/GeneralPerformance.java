package com.android.performanceprofilinglibrary.performance.models;

import android.util.SparseArray;

import com.android.performanceprofilinglibrary.performance.Utilities;

/**
 * Created by Sid on 15/07/16.
 */
public class GeneralPerformance {
    private String timestamp;
    private CpuInfo cpuInfo;
    private BatteryInfo batteryInfo;
    private MemoryData memoryData;
    private SparseArray<CustomAttributeData> customAttributes;

    public GeneralPerformance(CpuInfo cpuInfo, BatteryInfo batteryInfo, MemoryData memoryData,
                              SparseArray<CustomAttributeData> customAttributes) {
        this.cpuInfo = cpuInfo;
        this.batteryInfo = batteryInfo;
        this.memoryData = memoryData;
        this.customAttributes = customAttributes;
        setCurrentTimeStamp();
    }

    public GeneralPerformance(CpuInfo cpuInfo, BatteryInfo batteryInfo, MemoryData memoryData) {
        this.cpuInfo = cpuInfo;
        this.batteryInfo = batteryInfo;
        this.memoryData = memoryData;
        setCurrentTimeStamp();
    }

    public String getTimestamp() {
        return timestamp;
    }

    private void setCurrentTimeStamp() {
        timestamp = Utilities.getCurrentTimestamp();
    }

    public CpuInfo getCpuInfo() {
        return cpuInfo;
    }

    public void setCpuInfo(CpuInfo cpuInfo) {
        this.cpuInfo = cpuInfo;
    }

    public BatteryInfo getBatteryInfo() {
        return batteryInfo;
    }

    public void setBatteryInfo(BatteryInfo batteryInfo) {
        this.batteryInfo = batteryInfo;
    }

    public MemoryData getMemoryData() {
        return memoryData;
    }

    public void setMemoryData(MemoryData memoryData) {
        this.memoryData = memoryData;
    }

    public SparseArray<CustomAttributeData> getCustomAttributes() {
        return customAttributes;
    }

    public void setCustomAttributes(SparseArray<CustomAttributeData> customAttributes) {
        this.customAttributes = customAttributes;
    }
}
