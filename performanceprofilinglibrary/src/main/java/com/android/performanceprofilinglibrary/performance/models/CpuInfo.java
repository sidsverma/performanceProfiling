package com.android.performanceprofilinglibrary.performance.models;

/**
 * Created by Sid on 15/07/16.
 */
public class CpuInfo {
    private long processCpu;
    private long idleCpu;
    private long totalCpu;

    public CpuInfo(long processCpu, long idleCpu, long totalCpu) {
        this.processCpu = processCpu;
        this.idleCpu = idleCpu;
        this.totalCpu = totalCpu;
    }

    public CpuInfo() {
    }

    public void setProcessCpu(long processCpu) {
        this.processCpu = processCpu;
    }

    public void setIdleCpu(long idleCpu) {
        this.idleCpu = idleCpu;
    }

    public void setTotalCpu(long totalCpu) {
        this.totalCpu = totalCpu;
    }

    public long getProcessCpu() {
        return processCpu;
    }

    public long getIdleCpu() {
        return idleCpu;
    }

    public long getTotalCpu() {
        return totalCpu;
    }
}
