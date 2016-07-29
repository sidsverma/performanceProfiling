package com.android.performanceprofilinglibrary.performance;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import com.android.performanceprofilinglibrary.performance.models.BatteryInfo;
import com.android.performanceprofilinglibrary.performance.models.CpuInfo;
import com.android.performanceprofilinglibrary.performance.models.MemoryData;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Sid on 20/07/16.
 */
public class Utilities {
    public static BatteryInfo getBatteryState(Context context) {
        BatteryInfo batteryInfo = new BatteryInfo();
        try {
            Intent batteryIntent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            if( batteryIntent != null ) {
                int level = batteryIntent.getIntExtra( BatteryManager.EXTRA_LEVEL, -1 );
                int scale = batteryIntent.getIntExtra( BatteryManager.EXTRA_SCALE, -1 );

                batteryInfo.setPercentRemaining( ( (float) level / (float) scale ) * 100.0f );

                batteryInfo.setChargingState( batteryIntent.getIntExtra(BatteryManager.EXTRA_STATUS, -1 ) );

                batteryInfo.setBatteryState( batteryIntent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1 ) );
            }
        } catch( Exception e ) {
        } finally {
            return batteryInfo;
        }
    }

    public static CpuInfo getCpuInfo( int pid ) {
        String processPid = Integer.toString(pid);
        String cpuStatPath = "/proc/" + processPid + "/stat";
        CpuInfo cpuInfo = new CpuInfo();
        try {
            // monitor cpu stat of certain process
            RandomAccessFile processCpuInfo = new RandomAccessFile(cpuStatPath,
                    "r");
            String line = "";
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.setLength(0);
            while ((line = processCpuInfo.readLine()) != null) {
                stringBuffer.append(line + "\n");
            }
            String[] tok = stringBuffer.toString().split(" ");
            long processCpu = Long.parseLong(tok[13]) + Long.parseLong(tok[14]);
            cpuInfo.setProcessCpu( processCpu );
            processCpuInfo.close();
        } catch (FileNotFoundException e) {
//            Log.e(LOG_TAG, "FileNotFoundException: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            // monitor total and idle cpu stat of certain process
            RandomAccessFile cpuInfoFile = new RandomAccessFile("/proc/stat", "r");
            String[] toks = cpuInfoFile.readLine().split(" ");
            long idleCpu = Long.parseLong(toks[5]);
            cpuInfo.setIdleCpu( idleCpu );
            long totalCpu = Long.parseLong(toks[2]) + Long.parseLong(toks[3])
                    + Long.parseLong(toks[4]) + Long.parseLong(toks[6])
                    + Long.parseLong(toks[5]) + Long.parseLong(toks[7])
                    + Long.parseLong(toks[8]);
            cpuInfo.setTotalCpu( totalCpu );
            cpuInfoFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cpuInfo;
    }

    public static MemoryData getMemoryData( Context context ) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo( memoryInfo );

        MemoryData memoryData;
        if( android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN ) {
            memoryData = new MemoryData( memoryInfo.totalMem, memoryInfo.availMem,
                    memoryInfo.threshold, memoryInfo.lowMemory ? 1 : 2 );
        } else {
            memoryData = new MemoryData();
        }

        return memoryData;
    }

    public static String getCurrentTimestamp() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone("gmt"));
        Date date = new Date();
        return dateFormat.format(date);
    }
}
