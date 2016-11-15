package me.tahnok.alrm;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlarmActivity extends Activity {

    private static final long TEN_MINUTES = 10 * 60 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                                 | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                                 | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                                 | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                                 | WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);

        getActionBar().hide();

        setContentView(R.layout.activity_alarm);
        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed() {
    }

    @OnClick(R.id.exit_button)
    public void exitClick() {
        AlarmService.stopAlarm(getApplicationContext());
        finish();
    }

    @OnClick(R.id.snooze_button)
    public void snoozeClick() {
        Context context = getApplicationContext();
        AlarmService.stopAlarm(context);
        long tenMinutes = System.currentTimeMillis() + TEN_MINUTES;
        AlarmService.scheduleAlarm(context, tenMinutes);

        finish();
    }

    public static PendingIntent getPendingIntent(Context context) {
        Intent intent = new Intent(context, AlarmActivity.class);
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
