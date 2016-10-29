package me.tahnok.wesclock;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetAlarmActivity extends Activity implements TimePickerDialog.OnTimeSetListener, Network.Delegate {

    @BindView(R.id.current_time) TextView currentTimeView;
    @BindView(R.id.alarm_status) TextView alarmStatusView;

    private Alarm alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);

        ButterKnife.bind(this);
        alarm = Settings.getInstance(this).getTime();
        render();
    }

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
            case R.id.test_turn_on:
                testTurnOn();
                return true;
            case R.id.test_turn_off:
                testTurnOff();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void render() {
        currentTimeView.setText(alarm.toString());
        if (AlarmService.isAlarmPending(getApplicationContext())) {
            alarmStatusView.setText(R.string.alarm_set);
        } else {
            alarmStatusView.setText(R.string.alarm_not_set);
        }
    }

    @OnClick(R.id.current_time)
    protected void setTime() {
        new TimePickerDialog(this, this, alarm.getHour(), alarm.getMinute(), true).show();
    }

    @OnClick(R.id.set_alarm)
    protected void setAlarm() {
        AlarmService.scheduleAlarm(this, alarm);
        render();
    }

    @OnClick(R.id.clear_alarm)
    protected void clearAlarm() {
        AlarmService.clearPendingAlarm(getApplicationContext());
        render();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        alarm = new Alarm(hourOfDay, minute);
        Settings.getInstance(this).setAlarm(alarm);
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

    private void testTurnOff() {
        new NetworkTask().execute(new Pair(new Network(this), Network.Command.TURN_OFF));
    }

    public void testTurnOn() {
        new NetworkTask().execute(new Pair(new Network(this), Network.Command.TURN_ON));
    }

    @Override
    public void logError(Exception e, final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static PendingIntent getPendingIntent(Context context) {
        Intent intent = new Intent(context, SetAlarmActivity.class);
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

}
