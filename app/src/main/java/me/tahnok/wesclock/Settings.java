package me.tahnok.wesclock;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by wes on 2016-10-23.
 */

public class Settings {
    private static final String KEY_HOUR = "hour";
    private static final String KEY_MINUTE = "minute";

    private static Settings instance;
    private final SharedPreferences preferences;

    protected Settings(Context context) {
        preferences = context.getSharedPreferences("things", Context.MODE_PRIVATE);
    }

    public static Settings getInstance(Context context) {
        if (instance == null) {
            instance = new Settings(context);
        }
        return instance;
    }

    public Time getTime() {
        return new Time(getHour(), getMinute());
    }

    private int getMinute() {
        return preferences.getInt(KEY_MINUTE, 0);
    }

    private int getHour() {
        return preferences.getInt(KEY_HOUR, 7);
    }

    public void setTime(Time time) {
        preferences.edit()
            .putInt(KEY_HOUR, time.getHour())
            .putInt(KEY_MINUTE, time.getMinute()).apply();
    }

}
