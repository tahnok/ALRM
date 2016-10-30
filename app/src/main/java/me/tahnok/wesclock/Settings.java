package me.tahnok.wesclock;

import android.content.Context;
import android.content.SharedPreferences;

public class Settings {
    private static final String KEY_HOUR = "hour";
    private static final String KEY_MINUTE = "minute";
    private static final String KEY_PORT = "port";
    private static final String KEY_IP_ADDRESS = "ip_address";

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

    public Alarm getTime() {
        return new Alarm(getHour(), getMinute());
    }

    private int getMinute() {
        return preferences.getInt(KEY_MINUTE, 0);
    }

    private int getHour() {
        return preferences.getInt(KEY_HOUR, 7);
    }

    public void setAlarm(Alarm alarm) {
        preferences.edit()
            .putInt(KEY_HOUR, alarm.getHour())
            .putInt(KEY_MINUTE, alarm.getMinute())
            .apply();
    }

    public String getIpdAddress() {
        return preferences.getString(KEY_IP_ADDRESS, "");
    }

    public void setIpAddress(String ipAddress) {
        preferences
            .edit()
            .putString(KEY_IP_ADDRESS, ipAddress)
            .apply();
    }

    public int getPort() {
        return preferences.getInt(KEY_PORT, -1);
    }

    public void setPort(int port) {
        preferences
            .edit()
            .putInt(KEY_PORT, port)
            .apply();
    }


}
