package com.naeiut.plugins.backgroundstep;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import android.app.Activity;
import android.content.Intent;
import android.content.Context;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.work.WorkManager;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import java.util.concurrent.TimeUnit;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

@CapacitorPlugin(name = "Backgroundstep")
public class BackgroundstepPlugin extends Plugin {

    private Backgroundstep implementation = new Backgroundstep();

    private String TAG = "BackgroundstepPlugin";
    private SQLiteDatabase db;

    public static void startService(Context context, Activity activity) {

        if (!StepCountBackgroundService.isServiceRunning) {
          Intent serviceIntent = new Intent(context, StepCountBackgroundService.class);
          ContextCompat.startForegroundService(context, serviceIntent);
        }

        return;
    }

    public static void startServiceViaWorker(Context context) {
        String UNIQUE_WORK_NAME = "StartMyServiceViaWorker";
        WorkManager workManager = WorkManager.getInstance(context);

        // As per Documentation: The minimum repeat interval that can be defined is 15 minutes
        // (same as the JobScheduler API), but in practice 15 doesn't work. Using 16 here
        PeriodicWorkRequest request =
                new PeriodicWorkRequest.Builder(
                        StepWorker.class,
                        16,
                        TimeUnit.MINUTES)
                        .build();

        // to schedule a unique work, no matter how many times app is opened i.e. startServiceViaWorker gets called
        // do check for AutoStart permission
        workManager.enqueueUniquePeriodicWork(UNIQUE_WORK_NAME, ExistingPeriodicWorkPolicy.KEEP, request);
    }

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
    }

    @PluginMethod
    public void getToday(PluginCall call) {

        int step = StepCountDatabaseHelper.getTodayStep(this.db);

        JSObject response = new JSObject();
        response.put("count", step);

        call.resolve(response);
    }
}
