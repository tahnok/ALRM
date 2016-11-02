package me.tahnok.wesclock;

/**
 * Created by wes on 2016-11-01.
 */
public interface SettingsInterface {
    Alarm getTime();

    void setAlarm(Alarm alarm);

    String getIpdAddress();

    void setIpAddress(String ipAddress);

    int getPort();

    void setPort(int port);
}
