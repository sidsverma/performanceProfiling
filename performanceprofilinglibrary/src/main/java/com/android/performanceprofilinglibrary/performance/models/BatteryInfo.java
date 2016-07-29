package com.android.performanceprofilinglibrary.performance.models;

/**
 * Created by Sid on 15/07/16.
 */
public class BatteryInfo {
    private float percentRemaining;

    private int chargingState; // BATTERY_STATUS_UNKNOWN = 1; BATTERY_STATUS_CHARGING = 2;
    // BATTERY_STATUS_DISCHARGING = 3; BATTERY_STATUS_NOT_CHARGING = 4; BATTERY_STATUS_FULL = 5;

    private int batteryState; //BATTERY_HEALTH_UNKNOWN = 1; BATTERY_HEALTH_GOOD = 2; BATTERY_HEALTH_OVERHEAT = 3;
    // BATTERY_HEALTH_DEAD = 4; BATTERY_HEALTH_OVER_VOLTAGE = 5; BATTERY_HEALTH_UNSPECIFIED_FAILURE = 6;
    // BATTERY_HEALTH_COLD = 7;

    public BatteryInfo(float percentRemaining, int chargingState, int batteryState) {
        this.percentRemaining = percentRemaining;
        this.chargingState = chargingState;
        this.batteryState = batteryState;
    }

    public BatteryInfo(float percentRemaining, int chargingState) {
        this.percentRemaining = percentRemaining;
        this.chargingState = chargingState;
    }

    public BatteryInfo() {
    }

    public void setPercentRemaining(float percentRemaining) {
        this.percentRemaining = percentRemaining;
    }

    public void setChargingState(int chargingState) {
        this.chargingState = chargingState;
    }

    public void setBatteryState(int batteryState) {
        this.batteryState = batteryState;
    }

    public float getPercentRemaining() {
        return percentRemaining;
    }

    public int getChargingState() {
        return chargingState;
    }

    public int getBatteryState() {
        return batteryState;
    }
}
