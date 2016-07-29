# Performance Profiling
## About
This library helps you measure the performance of your app while it is in production. This library contains the models of various types of fields to be measured while finding out the performance and expects you to create your db(shown here) and transfer the data to your server which should accept this data(shown here).
It does 2 types of performance recording:
1. On any exception, you can store the related content related to the stack trace, etc.
2. If there is a task around which you need to measure how much of your cpu, memory and battery has been utilized, this library provides a way to do this too.

##  Android SDK integration

### Installation in Android Studio
Add dependencies to *app/build.gradle*:
```sh
compile 'com.android.performance:performanceprofilinglibrary:1.1'
```
Check out https://bintray.com/sidsverma/maven/performance-profiling for the latest version of the sdk.
# Using the SDK
### Creating the db
Define the following in your DatabaseHelper class(Define the values for the various keys appropriately):
```sh
//ErrorStack table create statement
private static final String CREATE_TABLE_ERROR_STACK = "CREATE TABLE " + TABLE_ERROR_STACK
        + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + KEY_TIMESTAMP +  " TEXT DEFAULT '', " +
        KEY_APP_NAME + " TEXT DEFAULT '', " + KEY_LIB_VERSION +  " TEXT DEFAULT '', " +
        KEY_PRIORITY +  " INTEGER DEFAULT 5, " + KEY_GENERAL_PERFORMANCES + " TEXT DEFAULT '', " +
        KEY_CRASH_INFO +  " TEXT DEFAULT '', " + KEY_CUSTOM_ATTRIBUTES +  " TEXT DEFAULT '' )";

//AroundFunctionality table create statement
private static final String CREATE_TABLE_AROUND_FUNCTIONALITY = "CREATE TABLE " + TABLE_AROUND_FUNCTIONALITY
        + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + KEY_TIMESTAMP +  " TEXT DEFAULT '', " +
        KEY_TITLE +  " TEXT DEFAULT '', " + KEY_DESCRIPTION +  " TEXT DEFAULT '', " +
        KEY_START_TIME + " TEXT DEFAULT '', " + KEY_END_TIME +  " TEXT DEFAULT '', " +
        KEY_RESULT +  " TEXT DEFAULT '', " +
        KEY_APP_NAME + " TEXT DEFAULT '', " + KEY_LIB_VERSION + " TEXT DEFAULT '', " +
        KEY_GENERAL_PERFORMANCES + " TEXT DEFAULT '', " + KEY_CUSTOM_ATTRIBUTES +  " TEXT DEFAULT '' )";
```


In the *onCreate()* function of your DatabaseHelper class, execute these 2 statements:
```sh
db.execSQL(CREATE_TABLE_ERROR_STACK);
db.execSQL(CREATE_TABLE_AROUND_FUNCTIONALITY);
```

Add the following methods in your DatabaseHelper class(Define the key values appropriately):
```sh
private String private_get_custom_attributes_json_from_array(SparseArray<CustomAttributeData> customAttributes) throws JSONException {
        List<JSONObject> reqd_gens = new ArrayList<>();
        for( int i = 0; i < customAttributes.size(); i++ ) {
            JSONObject gen = new JSONObject();
            gen.put( customAttributes.keyAt( i ) + "", customAttributes.valueAt( i ).toString());
            reqd_gens.add( gen );
        }
        return reqd_gens.toString();
    }

    private String private_get_general_performance_json_from_array(SparseArray<GeneralPerformance> generalPerformances) throws JSONException {
        List<JSONObject> reqd_gens = new ArrayList<>();
        for( int i = 0; i < generalPerformances.size(); i++ ) {
            JSONObject gen = new JSONObject();
            GeneralPerformance generalPerformance = generalPerformances.valueAt(i);
            gen.put( KEY_TIMESTAMP, generalPerformance.getTimestamp() );
            BatteryInfo batteryInfo = generalPerformances.valueAt(i).getBatteryInfo();
            gen.put( KEY_BATTERY_REMAINING, batteryInfo.getPercentRemaining() );
            gen.put( KEY_BATTERY_STATE, batteryInfo.getBatteryState() );
            gen.put( KEY_CHARGING_STATE, batteryInfo.getChargingState() );

            CpuInfo cpuInfo = generalPerformance.getCpuInfo();
            gen.put( KEY_TOTAL_CPU, cpuInfo.getTotalCpu() );
            gen.put( KEY_PROCESS_CPU, cpuInfo.getProcessCpu() );
            gen.put( KEY_IDLE_CPU, cpuInfo.getIdleCpu() );

            MemoryData memoryData = generalPerformance.getMemoryData();
            gen.put( KEY_TOTAL_MEMORY, memoryData.getTotalMemory() );
            gen.put( KEY_MEMORY_THRESHOLD, memoryData.getThreshold() );
            gen.put( KEY_AVAILABLE_MEMORY, memoryData.getAvailableMemory() );
            gen.put( KEY_IS_LOW_MEMORY, memoryData.isLowMemory() );

            gen.put( KEY_CUSTOM_ATTRIBUTES, generalPerformance.getCustomAttributes() );

            reqd_gens.add(gen);
        }
        return reqd_gens.toString();
    }

    private String private_get_crash_info_json_from_array(ApplicationErrorReport.CrashInfo crashInfo) throws JSONException {
        JSONObject gen = new JSONObject();
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH ) {
            gen.put(KEY_EXCEPTION_CLASS_NAME, crashInfo.exceptionClassName);
            gen.put(KEY_EXCEPTION_MESSAGE, crashInfo.exceptionMessage);
            gen.put(KEY_THROW_FILE_NAME, crashInfo.throwFileName);
            gen.put(KEY_THROW_LINE_NUMBER, crashInfo.throwLineNumber);
            gen.put(KEY_THROW_METHOD_NAME, crashInfo.throwMethodName);
            gen.put(KEY_STACK_TRACE, crashInfo.stackTrace);
        }
        return gen.toString();
    }

    private long private_insert_around_functionality(AroundFunctionalityData aroundFunctionalityData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(KEY_ID, msg.getId()); --Removed to get default autoincremented id from sqllite
        try {
            values.put(KEY_START_TIME, aroundFunctionalityData.getStartTime());
        } catch (Exception e) {
        }
        try {
            values.put(KEY_END_TIME, aroundFunctionalityData.getEndTime());
        } catch (Exception e) {
        }
        try {
            values.put(KEY_TITLE, aroundFunctionalityData.getTitle());
        } catch (Exception e) {
        }
        try {
            values.put(KEY_DESCRIPTION, aroundFunctionalityData.getDescription());
        } catch (Exception e) {
        }
        try {
            values.put(KEY_RESULT, aroundFunctionalityData.getResult());
        } catch (Exception e) {
        }
        try {
            values.put(KEY_APP_NAME, aroundFunctionalityData.getBasicInfo().getAppName() );
        } catch( Exception e ) {}
        try {
            values.put(KEY_LIB_VERSION, aroundFunctionalityData.getBasicInfo().getLibVersion() );
        } catch( Exception e ) {}
        try {
            values.put(KEY_GENERAL_PERFORMANCES,
                    private_get_general_performance_json_from_array(
                            aroundFunctionalityData.getGeneralPerformances() ) );
        } catch( Exception e ) {}
        try {
            values.put(KEY_CUSTOM_ATTRIBUTES,
                    private_get_custom_attributes_json_from_array(
                            aroundFunctionalityData.getCustomAttributes() ));
        } catch (Exception e) {
        }
        try {
            values.put(KEY_TIMESTAMP, aroundFunctionalityData.getTimestamp());
        } catch (Exception e) {
        }
        // insert values in AroundFunctionality
        long id = db.insert(TABLE_AROUND_FUNCTIONALITY, null, values);

        return id;
    }

    private long private_insert_error_stack_data(ErrorStackData errorStackData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(KEY_ID, msg.getId()); --Removed to get default autoincremented id from sqllite
        try {
            values.put(KEY_PRIORITY, errorStackData.getPriority());
        } catch (Exception e) {
        }
        try {
            values.put(KEY_APP_NAME, errorStackData.getBasicInfo().getAppName() );
        } catch( Exception e ) {}
        try {
            values.put(KEY_LIB_VERSION, errorStackData.getBasicInfo().getLibVersion() );
        } catch( Exception e ) {}
        try {
            SparseArray<GeneralPerformance> genr = new SparseArray<>();
            genr.put(0,
                    errorStackData.getGeneralPerformances());
            values.put(KEY_GENERAL_PERFORMANCES,
                    private_get_general_performance_json_from_array( genr ) );
        } catch( Exception e ) {}
        try {
            values.put(KEY_CRASH_INFO,
                    private_get_crash_info_json_from_array(
                            errorStackData.getCrashInfo() ) );
        } catch( Exception e ) {}
        try {
            values.put(KEY_CUSTOM_ATTRIBUTES,
                    private_get_custom_attributes_json_from_array(
                            errorStackData.getCustomAttributes() ));
        } catch (Exception e) {
        }
        try {
            values.put(KEY_TIMESTAMP, errorStackData.getTimestamp());
        } catch (Exception e) {
        }
        // insert values in Error Stack Data
        long id = db.insert(TABLE_ERROR_STACK, null, values);

        return id;
    }
    
    private JSONObject private_get_error_stack_json_from_cursor(Cursor cur) {
        JSONObject json = new JSONObject();
        try {
            json.put(KEY_ID, cur.getLong(cur.getColumnIndex(KEY_ID)));
        } catch (Exception e) {
        }
        try {
            json.put(KEY_PRIORITY, cur.getInt(cur.getColumnIndex(KEY_PRIORITY)));
        } catch (Exception e) {
        }
        try {
            json.put(KEY_APP_NAME, cur.getString(cur.getColumnIndex(KEY_APP_NAME)));
        } catch (Exception e) {
        }
        try {
            json.put(KEY_LIB_VERSION, cur.getString(cur.getColumnIndex(KEY_LIB_VERSION)));
        } catch (Exception e) {
        }
        try {
            json.put(KEY_CRASH_INFO, cur.getString(cur.getColumnIndex(KEY_CRASH_INFO)));
        } catch (Exception e) {
        }
        try {
            json.put(KEY_GENERAL_PERFORMANCES, cur.getString(cur.getColumnIndex(KEY_GENERAL_PERFORMANCES)));
        } catch (Exception e) {
        }
        try {
            json.put(KEY_CUSTOM_ATTRIBUTES, cur.getString(cur.getColumnIndex(KEY_CUSTOM_ATTRIBUTES)));
        } catch (Exception e) {
        }
        try {
            json.put(KEY_TIMESTAMP, cur.getString(cur.getColumnIndex(KEY_TIMESTAMP)));
        } catch (Exception e) {
        }
        return json;
    }

    private JSONObject private_get_around_functionality_json_from_cursor(Cursor cur) {
        JSONObject json = new JSONObject();
        try {
            json.put(KEY_ID, cur.getLong(cur.getColumnIndex(KEY_ID)));
        } catch (Exception e) {
        }
        try {
            json.put(KEY_START_TIME, cur.getLong(cur.getColumnIndex(KEY_START_TIME)));
        } catch (Exception e) {
        }
        try {
            json.put(KEY_END_TIME, cur.getLong(cur.getColumnIndex(KEY_END_TIME)));
        } catch (Exception e) {
        }
        try {
            json.put(KEY_TITLE, cur.getString(cur.getColumnIndex(KEY_TITLE)));
        } catch (Exception e) {
        }
        try {
            json.put(KEY_DESCRIPTION, cur.getString(cur.getColumnIndex(KEY_DESCRIPTION)));
        } catch (Exception e) {
        }
        try {
            json.put(KEY_RESULT, cur.getString(cur.getColumnIndex(KEY_RESULT)));
        } catch (Exception e) {
        }
        try {
            json.put(KEY_APP_NAME, cur.getString(cur.getColumnIndex(KEY_APP_NAME)));
        } catch (Exception e) {
        }
        try {
            json.put(KEY_LIB_VERSION, cur.getString(cur.getColumnIndex(KEY_LIB_VERSION)));
        } catch (Exception e) {
        }
        try {
            json.put(KEY_GENERAL_PERFORMANCES, cur.getString(cur.getColumnIndex(KEY_GENERAL_PERFORMANCES)));
        } catch (Exception e) {
        }
        try {
            json.put(KEY_CUSTOM_ATTRIBUTES, cur.getString(cur.getColumnIndex(KEY_CUSTOM_ATTRIBUTES)));
        } catch (Exception e) {
        }
        try {
            json.put(KEY_TIMESTAMP, cur.getString(cur.getColumnIndex(KEY_TIMESTAMP)));
        } catch (Exception e) {
        }
        return json;
    }

    public long insertAroundFunctionalityRow(AroundFunctionalityData aroundFunctionalityData) {
        return private_insert_around_functionality( aroundFunctionalityData );
    }

    public long insertErrorStackRow(ErrorStackData errorStackData) {
        return private_insert_error_stack_data( errorStackData );
    }

    public String fetch_performance_data() {
        List<JSONObject> reqdPerformanceMetrics = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_ERROR_STACK;
        String selectQuery2 = "SELECT * FROM " + TABLE_AROUND_FUNCTIONALITY;

        SQLiteDatabase db = null;
        Cursor cur = null;
        try {
            db = this.getReadableDatabase();
            cur = db.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if( cur.moveToFirst() ) {
                do {
                    JSONObject error_stack = private_get_error_stack_json_from_cursor(cur);
                    reqdPerformanceMetrics.add(error_stack);
                    // adding to msgs list
                } while ( cur.moveToNext() );
            }
        } catch( Exception e ) {}
        try {
            db = this.getReadableDatabase();
            cur = db.rawQuery(selectQuery2, null);
            // looping through all rows and adding to list
            if( cur.moveToFirst() ) {
                do {
                    JSONObject gen = private_get_around_functionality_json_from_cursor(cur);
                    reqdPerformanceMetrics.add(gen);
                    // adding to msgs list
                } while ( cur.moveToNext() );
            }
        } catch( Exception e ) {
        } finally {
            if( cur != null )
                cur.close();
            if( db != null )
                db.close();
        }
        //JSONArray reqd_messages_json = new JSONArray(reqd_messages);
        return reqdPerformanceMetrics.toString();
    }
```
##### Use case 1: Exception Handling
Now call this method in case of any exception like this:
```sh
 catch( Exception e ) {
    try {
        ErrorStackData errorStackData = new ErrorStackData(priority, new GeneralPerformance(
                Utilities.getCpuInfo(android.os.Process.myPid()), Utilities.getBatteryState(context),
                Utilities.getMemoryData( context ) ), new ApplicationErrorReport.CrashInfo(e.fillInStackTrace()));
        DatabaseHelper db = new DatabaseHelper( context );
        db.insertErrorStackRow( errorStackData );
        db.close();
    } catch( Exception ignore ) {}
}
```
##### Use case 2: General Performance Measurement
To begin with this periodic measurement, the following code should be invoked:
```sh
basicInfo = new BasicInfo( context.getPackageName(), BuildConfig.VERSION_NAME);
long endTime, startTime = Debug.threadCpuTimeNanos();
String result, title = "SomeTask", description = "The usual task";
SparseArray<CustomAttributeData> customAttributes = null;
if( backgroundExecutor == null )
    backgroundExecutor = new ScheduledThreadPoolExecutor(1);
else
    backgroundExecutor.shutdownNow();
final ScheduledFuture<?> schedulerHandle = backgroundExecutor.scheduleAtFixedRate(
        generalPerformanceObjectRunnable, 0, 500, TimeUnit.MILLISECONDS); // 500 ms is the PERIODICITY_OF_CALL.

backgroundExecutor.schedule(new Runnable() {
    public void run() { schedulerHandle.cancel(true); }
}, MAX_SECONDS_TO_RUN, TimeUnit.SECONDS); // Set this value to the maximum number of seconds you want the performance measurement to run(in case the 'stop' module is called by then).
```

To stop this periodic measurement, call the following:
```sh
endTime = Debug.threadCpuTimeNanos();
result = "SomeTask: failed/succeeded";
backgroundExecutor.shutdownNow();
AroundFunctionalityData aroundFunctionalityData = new AroundFunctionalityData( title, description,
        startTime, endTime, result, basicInfo,
        generalPerformances, customAttributes );
        
    db.insertAroundFunctionalityRow(aroundFunctionalityData);
    db.close();
```
### Sending the data
Send the json data in your hashmap by calling this:
```sh
DatabaseHelper db = new DatabaseHelper( context )
db.fetch_performance_data();
```
whenever you want to send this data accross to your backend server.
Also, you can use the following function to empty the data in your code, in case you need to(inside *DatabaseHelper.java*):
```sh
private void private_resetPerformanceMetricsTables() {
    SQLiteDatabase db = this.getWritableDatabase();
    db.execSQL("delete from " + TABLE_ERROR_STACK);
    db.execSQL("delete from " + TABLE_AROUND_FUNCTIONALITY);;
}
```

## Enjoy!
In case you have any queries or suggestions, feel free to ping me @sidsverma on twitter. Also, feel free to create a pull request for any enhancements! I'll keep enhancing this as time permits.
