package me.tahnok.wesclock;

import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.TimePicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetAlarmActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    @BindView(R.id.current_time) TextView currentTimeView;
    @BindView(R.id.alarm_status) TextView alarmStatusView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_set_alarm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.test_alarm:
                testAlarm();
                return true;
            case R.id.test_service:
                testService();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private Time time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        time = Settings.getInstance(this).getTime();
        render();
    }

    protected void render() {
        currentTimeView.setText(time.toString());
        if (AlarmService.isAlarmPending(getApplicationContext())) {
            alarmStatusView.setText("Alarm is set");
        } else {
            alarmStatusView.setText("Alarm is not set");
        }
    }

    @OnClick(R.id.current_time)
    protected void setTime() {
        new TimePickerDialog(this, this, time.getHour(), time.getMinute(), true).show();
    }

    @OnClick(R.id.set_alarm)
    protected void setAlarm() {
        AlarmService.scheduleAlarm(this, time);
        render();
    }

    @OnClick(R.id.clear_alarm)
    protected void clearAlarm() {
        AlarmService.clearPendingAlarm(getApplicationContext());
        render();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        time = new Time(hourOfDay, minute);
        Settings.getInstance(this).setTime(time);
        render();
    }

    public void testAlarm() {
        Intent intent = new Intent(this, AlarmActivity.class);
        startActivity(intent);
    }

    private void testService() {
        long tenSecondsFromNow = System.currentTimeMillis() + (10 * 1000);
        AlarmService.scheduleAlarm(this, tenSecondsFromNow);
    }


    public static PendingIntent getPendingIntent(Context context) {
        Intent intent = new Intent(context, SetAlarmActivity.class);
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

}
