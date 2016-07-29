package com.android.performanceprofilinglibrary.performance.models;

/**
 * Created by Sid on 15/07/16.
 */
public class MemoryData {
//    private Debug.MemoryInfo;
    private long totalMemory;
    private long availableMemory;
    private long threshold;
    private int lowMemory;

    public MemoryData(long totalMemory, long availableMemory,
                      long threshold, int lowMemory) {
        this.totalMemory = totalMemory;
        this.threshold = threshold;
        this.availableMemory = availableMemory;
        this.lowMemory = lowMemory;
    }

    public MemoryData() {}


    public void setTotalMemory(long totalMemory) {
        this.totalMemory = totalMemory;
    }

    public void setThreshold(long threshold) {
        this.threshold = threshold;
    }

    public void setLowMemory(int lowMemory) {
        this.lowMemory = lowMemory;
    }

    public void setAvailableMemory(long availableMemory) {
        this.availableMemory = availableMemory;
    }

    public long getAvailableMemory() {
        return availableMemory;
    }

    public long getTotalMemory() {
        return totalMemory;
    }

    public long getThreshold() {
        return threshold;
    }

    public int isLowMemory() {
        return lowMemory;
    }
}
