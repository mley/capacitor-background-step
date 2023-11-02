package com.naeiut.plugins.backgroundstep;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.app.Service;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.util.Log;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;

public class StepCountBackgroundService extends Service {

  private static final String TAG = "BackgroundService";

  private StepCountHelper stepCountHelper;

  public static boolean isServiceRunning;
  private String CHANNEL_ID = "sp_walk";
  private Context context;

  public StepCountBackgroundService() {
    Log.d(TAG, "constructor called");
    isServiceRunning = false;
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {

    Log.d(TAG, "onStartCommand called");
    Intent notificationIntent = new Intent(this, com.getcapacitor.BridgeActivity.class);
    PendingIntent pendingIntent = PendingIntent.getActivity(this,
      0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

    Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
      .setContentTitle(getString(R.string.notification_title))
      .setContentText(getString(R.string.notification_text))
//      .setSmallIcon(R.drawable.ic_launcher_background)
      .setSmallIcon(R.mipmap.ic_launcher_foreground)
      .setContentIntent(pendingIntent)
      .setAutoCancel(true)
//      .setColor(getResources().getColor(R.color.colorPrimary))
      .build();

    startForeground(1, notification);

    return START_STICKY;
  }

  public static void stopForegroundService(Context context, Activity activity) {
    StepCountHelper stepCountHelper = new StepCountHelper(context);
    stepCountHelper.stop();
    StepCountBackgroundService service = new StepCountBackgroundService();
    service.stopSelf();
    isServiceRunning = false;
  }

  @Override
  public void onCreate() {
    super.onCreate();

    int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION);
    if(permission == PackageManager.PERMISSION_GRANTED) {
//    Toast.makeText(this, "Service on create2", Toast.LENGTH_SHORT).show();
      this.stepCountHelper = new StepCountHelper(getApplicationContext());
      this.stepCountHelper.start();
      createNotificationChannel();
      isServiceRunning = true;
    }

  }

  @Override
  public IBinder onBind(Intent intent) {
      // We don't provide binding, so return null
      return null;
  }

  private void createNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      String appName = getString(R.string.app_name);
      NotificationChannel serviceChannel = new NotificationChannel(
        CHANNEL_ID,
        appName,
        NotificationManager.IMPORTANCE_DEFAULT
      );
      NotificationManager manager = getSystemService(NotificationManager.class);
      manager.createNotificationChannel(serviceChannel);
    }
  }

  @Override
  public void onDestroy() {

    isServiceRunning = false;
    stopForeground(true);

    Intent restartService = new Intent(this.context,RestartService.class);
    sendBroadcast(restartService);

    super.onDestroy();
  }

}
