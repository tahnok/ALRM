package me.tahnok.wesclock;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlarmActivity extends AppCompatActivity {

    protected Ringtone ringtone;

    @Override
    protected void onPause() {
        super.onPause();
//        ringtone.stop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        ButterKnife.bind(this);

//        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//        ringtone = RingtoneManager.getRingtone(getApplicationContext(), notification);
//        ringtone.play();
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
