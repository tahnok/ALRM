package me.tahnok.wesclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;


public class AlarmService extends Service implements Network.Delegate {

    public static final String EXTRA_COMMAND = "command";
    private static final int START_ALARM = 101;
    private static final int STOP_ALARM = 102;
    private static final String TAG = AlarmService.class.getSimpleName();

    protected Network network;
    private Ringtone ringtone;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(getApplicationContext(), notification);
        network = new Network(AlarmService.this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            return START_NOT_STICKY;
        }
        int command = intent.getIntExtra(EXTRA_COMMAND, START_ALARM);
        switch (command) {
            case START_ALARM:
                playAlarm();
                break;
            case STOP_ALARM:
                stopAlarm();
                break;
            default:
                Toast.makeText(getApplicationContext(), "??????????", Toast.LENGTH_LONG).show();
        }

        return START_STICKY;
    }

    protected void playAlarm() {
        ringtone.play();

        Intent intent = new Intent(getApplicationContext(), AlarmActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        turnOn();
    }

    private void turnOn() {
        new NetworkTask().execute(new Pair(network, Network.Command.TURN_ON));
    }

    private void turnOff() {
        new NetworkTask().execute(new Pair(network, Network.Command.TURN_OFF));
    }

    protected void stopAlarm() {
        ringtone.stop();
        Toast.makeText(getApplicationContext(), "Stopped", Toast.LENGTH_LONG).show();
        turnOff();
    }

    @Override
    public void logError(Exception e, String message) {
        Log.e(TAG, message, e);
    }

    public static void clearPendingAlarm(Context context) {
        getPendingIntent(context).cancel();
    }

    public static PendingIntent getPendingIntent(Context context) {
        Intent intent = new Intent(context, AlarmService.class);
        return PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static boolean isAlarmPending(Context context) {
        Intent intent = new Intent(context, AlarmService.class);
        return PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_NO_CREATE) != null;
    }


    public static void scheduleAlarm(Context context, Alarm alarm) {
        long alarmTime = alarm.getFutureOccurance();
        scheduleAlarm(context, alarmTime);
    }

    public static void scheduleAlarm(Context context, long alarmTime) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Log.e(TAG, "Alarm scheduled for " + alarmTime);

        PendingIntent editAlarmInfo = SetAlarmActivity.getPendingIntent(context);
        AlarmManager.AlarmClockInfo alarm = new AlarmManager.AlarmClockInfo(alarmTime, editAlarmInfo);

        PendingIntent alarmPendingIntent = AlarmService.getPendingIntent(context);
        manager.setAlarmClock(alarm, alarmPendingIntent);
    }

    public static void stopAlarm(Context context) {
        Intent intent = new Intent(context, AlarmService.class);
        intent.putExtra(EXTRA_COMMAND, STOP_ALARM);
        context.startService(intent);
    }

}
