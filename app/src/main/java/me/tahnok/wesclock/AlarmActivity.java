package me.tahnok.wesclock;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlarmActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.exit_button)
    public void exitClick() {
        AlarmService.stopAlarm(getApplicationContext());
        finish();
    }

    public static PendingIntent getPendingIntent(Context context) {
        Intent intent = new Intent(context, AlarmActivity.class);
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
