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
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetAlarmActivity extends Activity implements
    TimePickerDialog.OnTimeSetListener,
    Network.Delegate,
    CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.current_time) TextView currentTimeView;
    @BindView(R.id.alarm_switch) Switch alarmSwitch;

    @BindColor(R.color.alarm_enabled) int enabledColour;
    @BindColor(R.color.alarm_disabled) int disabledColour;

    private Alarm alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);

        ButterKnife.bind(this);

        alarmSwitch.setOnCheckedChangeListener(this);

        alarm = Settings.getInstance(this).getTime();
        render();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
                return true;
            case R.id.settings:
                launchSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void render() {
        currentTimeView.setText(alarm.toString());
        if (AlarmService.isAlarmPending(getApplicationContext())) {
            currentTimeView.setTextColor(enabledColour);
            currentTimeView.setEnabled(true);
        } else {
            currentTimeView.setTextColor(disabledColour);
        }
    }

    @OnClick(R.id.current_time)
    protected void setTime() {
        new TimePickerDialog(this, this, alarm.getHour(), alarm.getMinute(), true).show();
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            AlarmService.scheduleAlarm(getApplicationContext(), alarm);
        } else {
            AlarmService.clearPendingAlarm(getApplicationContext());
        }
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

    public void launchSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
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
